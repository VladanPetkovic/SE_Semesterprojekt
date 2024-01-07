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
    @Getter
    private MonsterType monsterType;

    Card(String name, float damage) {
        setName(name);
        setDamage(damage);
        setElementType();
        setMonsterType();
    }

    Card(Card other) {
        setId(other.getId());
        setName(other.getName());
        setDamage(other.getDamage());
        setElementType();
        setIsInDeck(getIsInDeck());
        setMonsterType();
    }

    Card(Card other, boolean isInDeck) {
        setId(other.getId());
        setName(other.getName());
        setDamage(other.getDamage());
        setElementType();
        setIsInDeck(isInDeck);
        setMonsterType();
    }

    public Card(org.example.backend.app.models.Card other) {
        setId(other.getCard_id());
        setName(other.getName());
        setDamage(other.getDamage());
        setElementType();
        setIsInDeck(false);
        setMonsterType();
    }

    public void setElementType() {
        switch (this.getName()) {
            case "WaterGoblin":
            case "WaterTroll":
            case "WaterElf":
            case "WaterSpell":
                this.elementType = ElementType.WATER;
                break;
            case "FireGoblin":
            case "FireTroll":
            case "FireElf":
            case "FireSpell":
                this.elementType = ElementType.FIRE;
                break;
            default:
                // Knight, Dragon, Ork, Kraken, Wizard and Regular-...
                this.elementType = ElementType.REGULAR;
        };
    }

    public void setMonsterType() {
        switch (this.getName()) {
            case "WaterGoblin":
            case "FireGoblin":
            case "RegularGoblin":
                this.monsterType = MonsterType.Goblin;
                break;
            case "WaterTroll":
            case "FireTroll":
            case "RegularTroll":
                this.monsterType = MonsterType.Troll;
                break;
            case "WaterElf":
            case "FireElf":
            case "RegularElf":
                this.monsterType = MonsterType.Elf;
                break;
            case "WaterSpell":
            case "FireSpell":
            case "RegularSpell":
                this.monsterType = MonsterType.Spell;
                break;
            default:
                // Knight, Dragon, Ork, Kraken, Wizard
                this.monsterType = MonsterType.Other;
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
