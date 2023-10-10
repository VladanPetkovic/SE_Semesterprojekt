package org.example.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private String name;
    public static short damage;
    private static byte elementType;
    private boolean isInDeck;
}
