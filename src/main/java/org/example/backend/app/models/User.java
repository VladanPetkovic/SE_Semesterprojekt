package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    @JsonAlias({"user_id"})
    @Setter(AccessLevel.PRIVATE)
    int user_id;
    @JsonAlias({"name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"elopoints"})
    @Setter(AccessLevel.PRIVATE)
    int elopoints;
    @JsonAlias({"coins"})
    @Setter(AccessLevel.PRIVATE)
    int coins;

    // Jackson needs the default constructor
    public User() {}
}
