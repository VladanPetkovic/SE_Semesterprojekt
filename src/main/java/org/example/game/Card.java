package org.example.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private String name;
    public static int damage;
    private static byte elementType;
    private boolean isInDeck;

    public void setCardInDeck()
    {

    }
}
