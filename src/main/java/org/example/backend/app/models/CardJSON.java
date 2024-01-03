package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class CardJSON {
    // total length 36 chars of UUID
    @JsonAlias({"Id"})
    String card_id;
    @JsonAlias({"Name"})
    String name;
    @JsonAlias({"Damage"})
    float damage;

    // Jackson needs the default constructor
    public CardJSON() {}
}
