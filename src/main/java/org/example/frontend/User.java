package org.example.frontend;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class User {
    private Profile profile;
    private int cardsInDeck = 0;

    User()
    {

    }

    public User(org.example.backend.app.models.User other) {
        this.profile = new Profile(other);
    }
    public void decreaseCoins(int decreaseValue) {
        this.profile.setCoins(this.profile.getCoins() - decreaseValue);
    }
}
