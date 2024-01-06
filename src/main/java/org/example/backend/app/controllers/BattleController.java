package org.example.backend.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.*;
import org.example.backend.app.repository.BattleRepository;
import org.example.backend.app.repository.CardRepository;
import org.example.backend.app.repository.UserRepository;
import org.example.backend.app.services.DatabaseService;
import org.example.backend.daos.CardDAO;
import org.example.backend.daos.UserDAO;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import org.example.backend.server.Response;
import org.example.frontend.Game;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class BattleController extends Controller {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private BattleRepository battleRepository;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Game game;
    private ReentrantLock lockFirstUser = new ReentrantLock();
    private ReentrantLock lockSecondUser = new ReentrantLock();

    public BattleController(BattleRepository battleRepository, Game game) {
        setBattleRepository(battleRepository);
        setGame(game);
    }

    public Response getStats(String token, DatabaseService databaseService) {
        try {
            // check, if login authorized
            if(token.isEmpty() || !checkAuthorization(token, false)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            }

            // get name and elo from user
            String name = this.game.getUsernameFromToken(token);
            User user = new UserRepository(new UserDAO(databaseService.getConnection())).get(name);

            // get count of wins and losses of user
            int win_count = getBattleRepository().getWinCount(user.getUser_id());
            int loss_count = getBattleRepository().getLossCount(user.getUser_id());


            // response
            UserStats responseUser = new UserStats(user, win_count, loss_count);
            String userDataJSON = getObjectMapper().writeValueAsString(responseUser);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    token,
                    "{ \"data\": " + userDataJSON + ", \"error\": null }"
            );

        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"data\": null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public Response getScoreboard(String token, DatabaseService databaseService) {
        try {
            // check, if login authorized
            if(token.isEmpty() || !checkAuthorization(token, false)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            }

            // get all users sorted by elopoints
            ArrayList<User> users = new  UserRepository(new UserDAO(databaseService.getConnection())).getAll();
            ArrayList<UserStats> responseUsers = new ArrayList<UserStats>();

            // bad for performance, but good enough for small list of users
            for(User user : users) {
                // get count of wins and losses of user
                int win_count = getBattleRepository().getWinCount(user.getUser_id());
                int loss_count = getBattleRepository().getLossCount(user.getUser_id());
                UserStats responseUser = new UserStats(user, win_count, loss_count);
                responseUsers.add(responseUser);
            }

            // response
            String userDataJSON = getObjectMapper().writeValueAsString(responseUsers);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    token,
                    "{ \"data\": " + userDataJSON + ", \"error\": null }"
            );

        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"data\": null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public Response runBattle(String token, DatabaseService databaseService) {

        // check, if login authorized
        if(token.isEmpty() || !checkAuthorization(token, false)) {
            return new Response(
                    HttpStatus.UNAUTHORIZED_ERROR,
                    ContentType.JSON,
                    "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
            );
        }

        // check, if user has set cards in deck
        int user_id = this.game.getUser(token).getProfile().getUser_id();
        ArrayList<Card> userCards =
                new CardRepository(new CardDAO(databaseService.getConnection())).getDeck(user_id);

        // user has no cards in deck
        if(userCards.isEmpty()) {
            return new Response(
                    HttpStatus.NO_CONTENT,
                    ContentType.JSON,
                    token,
                    "{ \"data\": null, \"error\": \"The request was fine, but the deck doesn't have any cards\" }"
            );
        }

        Battle newBattle = null;
        org.example.frontend.User waitingUser = this.game.getWaitingUserForBattle();

        // if no other player is in battle lobby
        if(waitingUser == null) {
            // set this player in lobby
            this.game.getUser(token).setInBattleLobby(true);

            lockFirstUser.lock();
            waitForSecondUser();
            lockFirstUser.unlock();
        } else {
            // remove other player from battle-lobby
            waitingUser.setInBattleLobby(false);

            lockSecondUser.lock();
            newBattle = startBattleWithSecondUser(token, waitingUser);
            // updating both users data
                // winner, looser
                // cards changed owner
            createBattle(newBattle);

            // update only, if a tie happened
            if(!newBattle.getTie()) {
                updateCardsOfUsers(token, waitingUser, databaseService);
            }
            updateEloOfUsers(token, waitingUser, databaseService);
            lockSecondUser.unlock();
        }

        // get Battle data
        Battle completedBattle = getBattleRepository().getLast();
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                token,
                completedBattle.getLog()
        );
    }

    public synchronized void createBattle(Battle battle) {
        if(battle != null) {
            getBattleRepository().add(battle);
        }
        // wake up other waiting thread
        this.notifyAll();
    }

    public void updateCardsOfUsers(String token, org.example.frontend.User firstUser, DatabaseService databaseService) {
        org.example.frontend.User secondUser = this.game.getUser(token);

        // update cards of first User
        ArrayList<org.example.frontend.Card> firstUserCards = firstUser.getDeck();
        if(firstUserCards != null) {
            for(org.example.frontend.Card cardFirstUser : firstUserCards) {
                String card_id = cardFirstUser.getId();
                new CardRepository(new CardDAO(databaseService.getConnection())).update(
                        card_id, firstUser.getProfile().getUser_id());
            }
        }

        // update cards of second User
        ArrayList<org.example.frontend.Card> secondUserCards = secondUser.getDeck();
        if(secondUserCards != null) {
            for(org.example.frontend.Card cardSecondUser : secondUserCards) {
                String card_id = cardSecondUser.getId();
                new CardRepository(new CardDAO(databaseService.getConnection())).update(
                        card_id, secondUser.getProfile().getUser_id());
            }
        }

        // remove deck of first User
        new CardRepository(new CardDAO(databaseService.getConnection())).removeDeck(firstUser.getProfile().getUser_id());

        // remove deck of second User
        new CardRepository(new CardDAO(databaseService.getConnection())).removeDeck(secondUser.getProfile().getUser_id());
    }

    public void updateEloOfUsers(String token, org.example.frontend.User firstUser, DatabaseService databaseService) {
        org.example.frontend.User secondUser = this.game.getUser(token);

        // update elo of first User
        int elo_firstUser = firstUser.getProfile().getEloPoints();
        int id_firstUser = firstUser.getProfile().getUser_id();
        new UserRepository(new UserDAO(databaseService.getConnection())).updateElo(id_firstUser, elo_firstUser);

        // update elo of second User
        int elo_secondUser = secondUser.getProfile().getEloPoints();
        int id_secondUser = secondUser.getProfile().getUser_id();
        new UserRepository(new UserDAO(databaseService.getConnection())).updateElo(id_secondUser, elo_secondUser);
    }

    public synchronized void waitForSecondUser() {
        // this player only waits to get picked up
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Battle startBattleWithSecondUser(String token, org.example.frontend.User firstUser) {
        org.example.frontend.User secondUser = this.game.getUser(token);

        // start battle
        return this.game.runNewBattle(firstUser, secondUser);
    }

    public boolean checkAuthorization(String token, boolean onlyAdmin) {
        return getGame().checkPlayerToken(token, onlyAdmin);
    }
}
