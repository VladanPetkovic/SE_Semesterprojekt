package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// todo: add database
public class User {
    private String username;
    private String password;
    private short coins;
    private Card[] stack;
    private int eloPoints;
    public static int userCount;

    public void setCredentials()
    {

    }
    public void decreaseCoins()
    {

    }
    public void manageCards()
    {

    }
    public void addCardToDeck()
    {

    }
    public void addUserCard(Card[] cardsToBeAdded)
    {

    }
    public void tradeCards(Card cardToBeRemoved)
    {

    }
    public void printDeck()
    {

    }
}
