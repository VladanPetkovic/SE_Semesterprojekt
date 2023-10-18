package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Card {
    @JsonAlias({"card_id"})
    int card_id;
    @JsonAlias({"fk_user_id"})
    int fk_user_id;
    @JsonAlias({"name"})
    String name;
    @JsonAlias({"damage"})
    int damage;
    @JsonAlias({"element_type"})
    int element_type;
    @JsonAlias({"isInDeck"})
    boolean isInDeck;

    // Jackson needs the default constructor
    public Card() {}
}
