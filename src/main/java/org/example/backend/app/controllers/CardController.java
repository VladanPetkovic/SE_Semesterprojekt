package org.example.backend.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Card;
import org.example.backend.app.models.CardJSON;
import org.example.backend.app.models.User;
import org.example.backend.app.repository.CardRepository;
import org.example.backend.app.repository.UserRepository;
import org.example.backend.app.services.DatabaseService;
import org.example.backend.daos.UserDAO;
import org.example.backend.http.Authorization;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import org.example.backend.server.Response;
import org.example.frontend.Game;

import java.util.ArrayList;
import java.util.Objects;

public class CardController extends Controller {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private CardRepository cardRepository;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Game game;

    public CardController(CardRepository cardRepository, Game game) {
        setCardRepository(cardRepository);
        setGame(game);
    }

    public synchronized Response createPackage(String body, String token) {
        try {
            // check, if admin is logged in
            if(token.isEmpty()) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            } else if(!checkAuthorization(token, true)) {
                // logged-in user is not admin
                return new Response(
                        HttpStatus.FORBIDDEN_ERROR,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"Provided user is not \"admin\"\" }"
                );
            }

            // check, if one card already exists
            ArrayList<CardJSON> newCards = getObjectMapper().readValue(body, new TypeReference<ArrayList<CardJSON>>() {});
            ArrayList<Card> checkCards = new ArrayList<Card>();

            for(CardJSON newCard : newCards) {
                Card checkCard = new Card(newCard);
                if(getCardRepository().get(checkCard.getCard_id()) != null) {
                    return new Response(
                            HttpStatus.CONFLICT,
                            ContentType.JSON,
                            token,
                            "{ \"data\": null, \"error\": \"At least one card in the packages already exists\" }"
                    );
                } else {
                    checkCards.add(checkCard);
                }
            }

            // create new cards --> because everything is fine
            for(Card checkCard : checkCards) {
                getCardRepository().add(checkCard);
            }
            System.out.println("New package created.");

            return new Response(
                    HttpStatus.CREATED,
                    ContentType.JSON,
                    token,
                    "{ \"data\": null, \"error\": \"null\" }"
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

    public synchronized Response acquirePackage(String token, DatabaseService databaseService) {
        try {
            // check, if login authorized
            if(token.isEmpty() || !checkAuthorization(token, false)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            }

            // check, if enough money available
            String name = this.game.getUsernameFromToken(token);
            User user = new UserRepository(new UserDAO(databaseService.getConnection())).get(name);

            if(user.getCoins() < 5) {
                return new Response(
                        HttpStatus.FORBIDDEN_ERROR,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"Not enough money for buying a card package\" }"
                );
            }

            // check, if a package is available for buying
            ArrayList<Card> cards_package = getCardRepository().getPackage();
            ArrayList<CardJSON> responseData = new ArrayList<CardJSON>();
            if(cards_package == null || cards_package.size() < 5) {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"No card package available for buying\" }"
                );
            }

            // buy package -- add package to card array
            for(Card i : cards_package) {       // frontend
                org.example.frontend.Card userCard = new org.example.frontend.Card(i);
                CardJSON cardData = new CardJSON(i);
                getGame().getUser(token).getStack().add(userCard);
                responseData.add(cardData);
            }
            getCardRepository().updatePackage(user.getUser_id());   // backend
            // decrease coins
            getGame().getUser(token).decreaseCoins();   // frontend
            int coins = getGame().getUser(token).getProfile().getCoins();   // backend
            new UserRepository(new UserDAO(databaseService.getConnection())).update(coins, user.getUser_id());  // backend

            // response
            String packageDataJSON = getObjectMapper().writeValueAsString(responseData);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    token,
                    "{ \"data\": " + packageDataJSON + ", \"error\": null }"
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

    public Response getCards(String token, DatabaseService databaseService) {
        try {
            // check, if login authorized
            if(token.isEmpty() || !checkAuthorization(token, false)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            }

            // check if user has cards
            String name = this.game.getUsernameFromToken(token);
            User user = new UserRepository(new UserDAO(databaseService.getConnection())).get(name);
            ArrayList<Card> userCards = getCardRepository().getAll(user.getUser_id());
            ArrayList<CardJSON> responseData = new ArrayList<CardJSON>();

            // user has no cards
            if(userCards.isEmpty()) {
                return new Response(
                        HttpStatus.NO_CONTENT,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"The request was fine, but the user doesn't have any cards\" }"
                );
            }

            // return cards
            for(Card i : userCards) {       // frontend
                CardJSON cardData = new CardJSON(i);
                responseData.add(cardData);
            }

            // test
            //getGame().getUser(token).printCards();

            // response
            String cardsDataJSON = getObjectMapper().writeValueAsString(responseData);
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    token,
                    "{ \"data\": " + cardsDataJSON + ", \"error\": null }"
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

    public synchronized Response getDeck(String token, String passedFormat, DatabaseService databaseService) {
        try {
            // check, if login authorized
            if(token.isEmpty() || !checkAuthorization(token, false)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            }

            // check if user has cards in deck
            String name = this.game.getUsernameFromToken(token);
            User user = new UserRepository(new UserDAO(databaseService.getConnection())).get(name);
            ArrayList<Card> userCards = getCardRepository().getDeck(user.getUser_id());
            ArrayList<CardJSON> responseData = new ArrayList<CardJSON>();

            // user has no cards in deck
            if(userCards.isEmpty()) {
                return new Response(
                        HttpStatus.NO_CONTENT,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"The request was fine, but the deck doesn't have any cards\" }"
                );
            }

            // return cards
            for(Card i : userCards) {       // frontend
                CardJSON cardData = new CardJSON(i);
                responseData.add(cardData);
            }

            // test
            //getGame().getUser(token).printCards();

            // response
            if(Objects.equals(passedFormat, "plain")) {
                String cardsDataPlain = getObjectMapper().writeValueAsString(responseData).replaceAll("\"", "");
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        token,
                        cardsDataPlain
                );
            } else {
                String cardsDataJSON = getObjectMapper().writeValueAsString(responseData);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        token,
                        "{ \"data\": " + cardsDataJSON + ", \"error\": null }"
                );
            }
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

    public synchronized Response updateDeck(String token, String body, DatabaseService databaseService) {
        try {
            // check, if login authorized
            if(token.isEmpty() || !checkAuthorization(token, false)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            }

            // check, if provided deck has four cards
            ArrayList<String> submittedDeck = getObjectMapper().readValue(body, new TypeReference<ArrayList<String>>() {});

            if(submittedDeck.size() != 4) {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"The provided deck did not include the required amount of cards\" }"
                );
            }

            // check, if all cards exists and are owned by the user
            String name = this.game.getUsernameFromToken(token);
            User user = new UserRepository(new UserDAO(databaseService.getConnection())).get(name);
            ArrayList<Card> checkCards = getCardRepository().getAll(user.getUser_id());

            if(!idsAreSame(submittedDeck, checkCards) || idsAreDuplicates(submittedDeck)) {
                return new Response(
                        HttpStatus.FORBIDDEN_ERROR,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"At least one of the provided cards does not belong to the user or is not available.\" }"
                );
            }

            // update deck
            // frontend
            getGame().getUser(token).setDeck(submittedDeck);
            getGame().getUser(token).printDeck();   // testing
            // backend
                // set all to false
            getCardRepository().removeDeck(user.getUser_id());
                // setting up the new deck
            for(String update_id : submittedDeck) {
                getCardRepository().updateDeck(update_id);
            }

            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    token,
                    "{ \"data\": null, \"error\": null }"
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

    public boolean idsAreSame(ArrayList<String> card_ids, ArrayList<Card> cards) {
        int counter_same_ids = 0;
        for(String card_id : card_ids) {
            for(Card card : cards) {
                if(Objects.equals(card.getCard_id(), card_id)) {
                    counter_same_ids++;
                }
            }
        }

        return counter_same_ids == 4;
    }

    public boolean idsAreDuplicates(ArrayList<String> card_ids) {
        int duplicates = 0;
        for(String id : card_ids) {
            for(String check_id : card_ids) {
                if(Objects.equals(id, check_id)) {
                    duplicates++;
                }
            }
        }
        return duplicates > 4;
    }
}

