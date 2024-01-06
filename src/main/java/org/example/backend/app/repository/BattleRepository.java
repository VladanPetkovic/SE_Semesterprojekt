package org.example.backend.app.repository;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Battle;
import org.example.backend.daos.BattleDAO;

import java.util.ArrayList;

public class BattleRepository implements Repository<Battle> {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    BattleDAO battleDAO;

    public BattleRepository(BattleDAO battleDAO) {
        setBattleDAO(battleDAO);
    }

    @Override
    public Battle get(int id) {
        return getBattleDAO().read(id);
    }

    public Battle getLast() { return getBattleDAO().readLast(); }

    public int getWinCount(int user_id) {
        return getBattleDAO().readWinnerCount(user_id);
    }

    public int getLossCount(int user_id) {
        return getBattleDAO().readLooserCount(user_id);
    }

    @Override
    public ArrayList<Battle> getAll() {
        return getBattleDAO().readAll();
    }

    @Override
    public void add(Battle battle) {
        getBattleDAO().create(battle);
    }

    @Override
    public void update(Battle battle) {
        getBattleDAO().update(battle);
    }

    @Override
    public void remove(int id) {
        getBattleDAO().delete(id);
    }
}
