package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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
    @JsonAlias({"tie"})
    @Setter(AccessLevel.PRIVATE)
    Boolean tie;

    // Jackson needs the default constructor
    public Battle() {}

    public Battle(org.example.frontend.Battle other, ArrayList<String> log) {
        setUser_winner_id(other.getWinner_id());
        setUser_looser_id(other.getLooser_id());
        setLog(String.join(" ", log));
        setTie(other.isTie());
    }
}
