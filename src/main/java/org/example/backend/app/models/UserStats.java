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
    @JsonAlias({"name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"elo"})
    @Setter(AccessLevel.PRIVATE)
    int elo;
    @JsonAlias({"wins"})
    @Setter(AccessLevel.PRIVATE)
    int wins;
    @JsonAlias({"losses"})
    @Setter(AccessLevel.PRIVATE)
    int losses;


    // Jackson needs the default constructor
    public UserStats() {}
}
