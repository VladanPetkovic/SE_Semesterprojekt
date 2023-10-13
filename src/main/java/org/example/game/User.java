package org.example.game;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class User {
    private Profile profile;
    private boolean isLoggedIn;
    private boolean deckIsFull = false;

    User()
    {

    }
    public void decreaseCoins(int decreaseValue)
    {
        this.profile.setCoins(this.profile.getCoins() - decreaseValue);
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
    public void addCardsToStack(ArrayList<Card> cardsToBeAdded)
    {
        this.profile.appendPackage(cardsToBeAdded);
    }
    public void printDeck()
    {

    }

}
