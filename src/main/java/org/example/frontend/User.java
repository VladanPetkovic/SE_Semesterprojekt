package org.example.frontend;

import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.CardJSON;

import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class User {
    private Profile profile;
    private ArrayList<Card> stack = new ArrayList<Card>();

    User()
    {

    }

    public User(org.example.backend.app.models.User other) {
        this.profile = new Profile(other);
    }
    public void decreaseCoins() {
        this.profile.setCoins(this.profile.getCoins() - 5);
    }
    public int countCardsInDeck() {
        int cardsInDeck = 0;
        for(Card card : this.stack) {
            if(card.getIsInDeck())
                cardsInDeck++;
        }
        return cardsInDeck;
    }
    public void printCards() {
        for(Card card : this.stack) {
            System.out.println("ID: " + card.getId());
            System.out.println("Name: " + card.getName());
            System.out.println("Damage: " + card.getDamage());
            System.out.println("IsInDeck: " + card.getIsInDeck());
            System.out.println("ElementType: " + card.getElementType().name());
            System.out.println();
        }
    }

    public void printDeck() {
        for(Card card : this.stack) {
            if(card.getIsInDeck()) {
                System.out.println("ID: " + card.getId());
                System.out.println("Name: " + card.getName());
                System.out.println("Damage: " + card.getDamage());
                System.out.println("IsInDeck: " + card.getIsInDeck());
                System.out.println("ElementType: " + card.getElementType().name());
                System.out.println();
            }
        }
    }

    public ArrayList<CardJSON> getDeck() {
        ArrayList<CardJSON> deck = new ArrayList<CardJSON>();
        for(Card card : this.stack) {
            if(card.getIsInDeck()) {
                deck.add(new CardJSON(card));
            }
        }
        return deck;
    }

    public void setDeck(ArrayList<String> card_ids) {
        // remove all cards from deck
        for(Card card : this.stack) {
            card.setIsInDeck(false);
        }

        // set new cards in deck
        for(String id : card_ids) {
            for(Card card : this.stack) {
                if(Objects.equals(card.getId(), id)) {
                    card.setIsInDeck(true);
                }
            }
        }
    }
}
