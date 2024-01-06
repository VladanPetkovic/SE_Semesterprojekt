package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.frontend.ElementType;

@Getter
@Setter
@AllArgsConstructor

public class Card {
    // total length 36 chars of UUID
    @JsonAlias({"card_id"})
    @Setter(AccessLevel.PRIVATE)
    String card_id;
    @JsonAlias({"user_id"})
    @Setter(AccessLevel.PRIVATE)
    int user_id;
    @JsonAlias({"name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"damage"})
    @Setter(AccessLevel.PRIVATE)
    float damage;
    @JsonAlias({"element_type"})
    @Setter(AccessLevel.PRIVATE)
    int element_type;
    @JsonAlias({"isInDeck"})
    @Setter(AccessLevel.PRIVATE)
    boolean isInDeck = false;

    // Jackson needs the default constructor
    public Card() {}

    public boolean getIsInDeck() {
        return this.isInDeck;
    }

    public Card(CardJSON other) {
        setCard_id(other.getCard_id());
        setName(other.getName());
        setDamage(other.getDamage());
        setElement_type(selectElementType(other.getName()));
    }

    public int selectElementType(String name) {
        return switch (name) {
            case "FireGoblin", "FireTroll", "FireElf", "FireSpell" -> ElementType.FIRE.ordinal();
            case "WaterGoblin", "WaterTroll", "WaterElf", "WaterSpell" -> ElementType.WATER.ordinal();
            default ->
                // Knight, Dragon, Ork, Kraken, "RegularGoblin", "RegularTroll", "RegularElf", "RegularSpell"
                    ElementType.REGULAR.ordinal();
        };
    }
}
