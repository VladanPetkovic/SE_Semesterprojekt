package org.example.frontend;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.User;
import org.example.backend.http.Authorization;

import java.util.ArrayList;

@Getter
@Setter
public class Profile {
    private int user_id;
    private String username;
    private String password;
    private String token;
    private int coins = 20;
    private int eloPoints = 100;
    private String bio;
    private String image;
    private ArrayList<Card> stack = new ArrayList<>();

    public Profile(User other) {
        setUser_id(other.getUser_id());
        setUsername(other.getName());
        setPassword(other.getPassword());
        setToken(new Authorization().getAuth(other.getName()));
        setCoins(other.getCoins());
        setEloPoints(other.getElopoints());
        setBio(other.getBio());
        setImage(other.getImage());
    }

    public int countCardsInDeck()
    {
        int cardsInDeck = 0;
        for(Card card : stack) {
            if(card.isInDeck())
                cardsInDeck++;
        }
        return cardsInDeck;
    }
}
