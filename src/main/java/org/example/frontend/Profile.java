package org.example.frontend;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Profile {
    private String username;
    private String password;
    private int coins = 20;
    private int eloPoints = 100;
    private ArrayList<Card> stack = new ArrayList<>();

    Profile(String username, String password)
    {
        this.username = username;
        this.password = password;
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
