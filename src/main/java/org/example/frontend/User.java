package org.example.frontend;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

@Getter
@Setter
public class User {
    private Profile profile;
    private ArrayList<Card> stack = new ArrayList<Card>();
    boolean isInBattleLobby = false;

    User() {

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

    public ArrayList<Card> getDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for(Card card : this.stack) {
            if(card.getIsInDeck()) {
                deck.add(new Card(card));
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

    public Card getRandomCardFromDeck() {
        ArrayList<Card> deck = getDeck();
        Random random = new Random();

        int randomCardIndex = random.nextInt(4);
        for(Card returnCard : deck) {
            if(randomCardIndex == 0) {
                return returnCard;
            }
            randomCardIndex--;
        }

        return deck.get(0);
    }

    public void removeCard(String card_id) {
        this.stack.removeIf(card -> Objects.equals(card.getId(), card_id));
    }
}
