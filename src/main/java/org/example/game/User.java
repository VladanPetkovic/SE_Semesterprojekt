package org.example.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Profile profile;
    private boolean isLoggedIn;
    private boolean deckIsFull = false;

    User()
    {

    }
    public void decreaseCoins()
    {

    }
    public void manageCards()
    {
        // check if stack is empty
        if(this.profile.getStack().isEmpty()) {
            System.out.println("You don't have any cards in your stack - purchase cards in the shop!");
            return;
        }
    }
    public void addCardToDeck()
    {

    }
    public void addUserCard(Card[] cardsToBeAdded)
    {

    }
    public void printDeck()
    {

    }

}
