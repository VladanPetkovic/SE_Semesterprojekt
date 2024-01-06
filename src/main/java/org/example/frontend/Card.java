package org.example.frontend;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Card {
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private float damage;
    @Getter
    private ElementType elementType;
    private boolean isInDeck;

    Card(int damage, ElementType et) {
        this.damage = damage;
        this.elementType = et;
    }

    Card(Card other) {
        setId(other.getId());
        setName(other.getName());
        setDamage(other.getDamage());
        setElementType(other.getElementType().ordinal());
        setIsInDeck(getIsInDeck());
    }

    Card(Card other, boolean isInDeck) {
        setId(other.getId());
        setName(other.getName());
        setDamage(other.getDamage());
        setElementType(other.getElementType().ordinal());
        setIsInDeck(isInDeck);
    }

    public Card(org.example.backend.app.models.Card other) {
        setId(other.getCard_id());
        setName(other.getName());
        setDamage(other.getDamage());
        setElementType(other.getElement_type());
        setIsInDeck(false);
    }

    public void setElementType(int value) {
        switch (value) {
            case 0 -> this.elementType = ElementType.FIRE;
            case 1 -> this.elementType = ElementType.WATER;
            case 2 -> this.elementType = ElementType.REGULAR;
            default ->
                // Knight, Dragon, Ork, Kraken
                    this.elementType = ElementType.NONE;
        };
    }
    public void setIsInDeck(boolean inDeck) {
        this.isInDeck = inDeck;
    }
    public boolean getIsInDeck() {
        return this.isInDeck;
    }

    public boolean isMonsterCard() {
        return !isSpellCard();
    }

    public boolean isSpellCard() {
        return Objects.equals(getName(), "WaterSpell") ||
                Objects.equals(getName(), "FireSpell") ||
                Objects.equals(getName(), "RegularSpell");
    }
}
