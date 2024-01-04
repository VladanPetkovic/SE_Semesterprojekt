package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Battle {
    @JsonAlias({"battle_id"})
    @Setter(AccessLevel.PRIVATE)
    int battle_id;
    @JsonAlias({"user_winner_id"})
    @Setter(AccessLevel.PRIVATE)
    int user_winner_id;
    @JsonAlias({"user_looser_id"})
    @Setter(AccessLevel.PRIVATE)
    int user_looser_id;
    @JsonAlias({"log"})
    @Setter(AccessLevel.PRIVATE)
    String log;
    @JsonAlias({"time"})
    @Setter(AccessLevel.PRIVATE)
    String time;

    // Jackson needs the default constructor
    public Battle() {}
}
