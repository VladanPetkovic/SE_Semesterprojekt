package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class CardJSON {
    // total length 36 chars of UUID
    @JsonAlias({"Id"})
    @Setter(AccessLevel.PRIVATE)
    String card_id;
    @JsonAlias({"Name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"Damage"})
    @Setter(AccessLevel.PRIVATE)
    float damage;

    // Jackson needs the default constructor
    public CardJSON() {}
}
