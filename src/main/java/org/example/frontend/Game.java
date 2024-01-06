package org.example.frontend;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Battle;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class Game {
    // Game has only logged-in users
    // --> no point in having all players from the database in game
    private ArrayList<User> users = new ArrayList<>();
    private Shop shop;

    public Game() {

    }

    public void addUserToGame(org.example.backend.app.models.User user) {
        users.add(new User(user));
    }

    public boolean checkPlayerToken(String token, boolean onlyAdmin) {
        // only admin has access
        if(onlyAdmin) {
            if(Objects.equals(token, "Bearer admin-mtcgToken")) {
                return true;
            }
        } else {
            for(User user : getUsers()) {
                // admin and user with correct token has access
                if(Objects.equals(user.getProfile().getToken(), token) ||
                    Objects.equals(token, "Bearer admin-mtcgToken")) {
                    return true;
                }
            }
        }

        return false;
    }

    public User getUser(String token) {
        for(User user : this.users) {
            if(Objects.equals(user.getProfile().getToken(), token)) {
                return user;
            }
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        for(User user : this.users) {
            if(Objects.equals(user.getProfile().getToken(), token)) {
                return user.getProfile().getUsername();
            }
        }
        return null;
    }

    public Battle runNewBattle(User userOne, User userTwo) {
        // start new battle
        org.example.frontend.Battle battle = new org.example.frontend.Battle(userOne, userTwo);
        battle.runBattle();

        return new Battle(battle, battle.getLog());
    }

    public synchronized User getWaitingUserForBattle() {
        // return first User, if on user is in battle-lobby
        // return null, if battle-lobby is empty

        // this sleep time gives other threads in queue enough time to complete their task
        // time is chosen randomly
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(User user : this.users) {
            if(user.isInBattleLobby()) {
                return user;
            }
        }

        return null;
    }

}
