package org.example.frontend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Profile profile;
    private boolean isLoggedIn;
    private int cardsInDeck = 0;

    User()
    {

    }
    public void decreaseCoins(int decreaseValue)
    {
        this.profile.setCoins(this.profile.getCoins() - decreaseValue);
    }
}
