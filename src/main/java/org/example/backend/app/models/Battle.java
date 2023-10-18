package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Battle {
    @JsonAlias({"battle_id"})
    int battle_id;
    @JsonAlias({"user_winner_id"})
    int user_winner_id;
    @JsonAlias({"user_looser_id"})
    int user_looser_id;
    @JsonAlias({"log"})
    String log;
    @JsonAlias({"time"})
    String time;

    // Jackson needs the default constructor
    public Battle() {}
}
