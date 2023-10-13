package org.example.game;

import lombok.Getter;
import lombok.Setter;

import javax.swing.text.Element;

@Getter
@Setter
public class Card {
    private String name;
    private final int damage;
    private final ElementType elementType;
    private boolean isInDeck;

    Card(int damage, ElementType et) {
        this.damage = damage;
        this.elementType = et;
    }

    public void setCardInDeck()
    {

    }
    public void makeDamage() {}    // overriding in child classes

}
