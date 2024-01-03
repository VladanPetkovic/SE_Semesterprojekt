package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserStats {
    @JsonAlias({"Name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"Elo"})
    @Setter(AccessLevel.PRIVATE)
    int elo;
    @JsonAlias({"Wins"})
    @Setter(AccessLevel.PRIVATE)
    int wins;
    @JsonAlias({"Losses"})
    @Setter(AccessLevel.PRIVATE)
    int losses;


    // Jackson needs the default constructor
    public UserStats() {}
}
