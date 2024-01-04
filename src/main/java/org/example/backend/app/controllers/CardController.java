package org.example.backend.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Card;
import org.example.backend.app.models.CardJSON;
import org.example.backend.app.repository.CardRepository;
import org.example.backend.http.Authorization;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import org.example.backend.server.Response;
import org.example.frontend.Game;

import java.util.ArrayList;

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

    public Response createPackage(String body, String token) {
        try {
            // check, if admin is logged in
            if(token.isEmpty()) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or is invalid\" }"
                );
            } else if(!checkAuthorization(token)) {
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

    public boolean checkAuthorization(String token) {
        return getGame().checkPlayerToken(token, true);
    }
}

