package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    @JsonAlias({"user_id"})
    int user_id;
    @JsonAlias({"name"})
    String name;
    @JsonAlias({"elopoints"})
    int elopoints;
    @JsonAlias({"coins"})
    int coins;

    // Jackson needs the default constructor
    public User() {}
}
