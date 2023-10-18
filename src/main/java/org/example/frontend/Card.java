package org.example.frontend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private String name;
    private final int damage;
    private final ElementType elementType;
    private boolean isInDeck = false;

    Card(int damage, ElementType et) {
        this.damage = damage;
        this.elementType = et;
    }

    public void setCardInDeck()
    {

    }
    public void makeDamage() {}    // overriding in child classes

}
