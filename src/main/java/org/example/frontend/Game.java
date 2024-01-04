package org.example.frontend;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class Game {
    // Game has only logged-in users
    // --> no point in having all players from the database in game
    private ArrayList<User> users = new ArrayList<>();
    private Statistics stats;
    private Shop shop;

    public Game()
    {

    }

    public void addUserToGame(org.example.backend.app.models.User user) {
        users.add(new User(user));
    }

    public boolean checkPlayerToken(String token, boolean onlyAdmin) {
        for(User user : getUsers()) {
            // only admin has access
            if(onlyAdmin) {
                if(Objects.equals(user.getProfile().getToken(), "Bearer admin-mtcgToken")) {
                    return true;
                }
            } else { // admin and user with correct token has access
                if(
                        Objects.equals(user.getProfile().getToken(), token) ||
                        Objects.equals(user.getProfile().getToken(), "Bearer admin-mtcgToken")
                ) {
                    return true;
                }
            }
        }
        return false;
    }

}
