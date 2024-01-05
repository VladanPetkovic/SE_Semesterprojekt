package org.example.backend.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Card;
import org.example.backend.app.models.CardJSON;
import org.example.backend.app.models.User;
import org.example.backend.app.models.UserStats;
import org.example.backend.app.repository.BattleRepository;
import org.example.backend.app.repository.UserRepository;
import org.example.backend.app.services.DatabaseService;
import org.example.backend.daos.UserDAO;
import org.example.backend.http.Authorization;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import org.example.backend.server.Response;
import org.example.frontend.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BattleController extends Controller {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private BattleRepository battleRepository;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Game game;

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


    public boolean checkAuthorization(String token, boolean onlyAdmin) {
        return getGame().checkPlayerToken(token, onlyAdmin);
    }
}
