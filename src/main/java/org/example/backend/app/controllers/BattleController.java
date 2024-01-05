package org.example.backend.app.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.repository.BattleRepository;
import org.example.frontend.Game;

public class BattleController extends Controller {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private BattleRepository battleRepository;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Game game;

    public BattleController(BattleRepository battleRepository, Game game) {
        setBattleRepository(battleRepository);
        setGame(game);
    }





    public boolean checkAuthorization(String token, boolean onlyAdmin) {
        return getGame().checkPlayerToken(token, onlyAdmin);
    }
}
