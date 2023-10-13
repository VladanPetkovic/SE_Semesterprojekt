package org.example.game;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Scanner;

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
    public void manageCards()
    {
        // check if stack is empty
        if(this.profile.getStack().isEmpty()) {
            System.out.println("You don't have any cards in your stack - purchase cards in the shop!");
            return;
        }
        // choose cards to remove or place in deck
        Scanner scanner = new Scanner(System.in);
        boolean wrongInputMade = false;
        int inputNumberOfCard;
        String charInput;

        do
        {
            // print user-cards
            printStack();
            if(wrongInputMade) {
                printInstructions(wrongInputMade);
                wrongInputMade = false;
            } else {
                printInstructions(wrongInputMade);
            }
            // get Input
            charInput = scanner.nextLine();
            // process the input
            if(charInput.equals("+")) {
                addCardToDeck();
            } else if(charInput.equals("-")) {
                removeCardFromDeck();
            } else if(!charInput.equalsIgnoreCase("save")) {
                wrongInputMade = true;
            }
        }while(!charInput.equalsIgnoreCase("save"));
    }
    public void printInstructions(boolean wrongInputMade)
    {
        for(int i = 0; i < 4; i++) {
            System.out.println();
        }
        System.out.println("Add to or remove cards from your deck: ");
        System.out.println("\tType \"+\" to add the selected card to your deck.");
        System.out.println("\tType \"-\" to remove the selected card from your deck.");
        System.out.println("\tType \"save\" to save your changes and return to the previous menu.");
        System.out.println("\tNext:");
        System.out.println("\t\tSelect the card by entering the No. - for example: \"12\"");
        System.out.println("\tNumber of cards to place in deck: " + (4 - this.profile.countCardsInDeck()));
        if(wrongInputMade) {
            System.out.println("\t-----Wrong input - Try again!-----");
        }
        System.out.print("\t\tType here: ");
    }
    private void addCardToDeck()
    {
        Scanner scanner = new Scanner(System.in);
        // getting second user-input --> card-number to perform selected action
        System.out.print("Number of card: ");
        int cardNumber = scanner.nextInt();
        // when something goes wrong, we will return true to output an error message

        // invalid number passed by user
        if(cardNumber < 1 || cardNumber > this.profile.getStack().size()) {
            System.out.println("Number out of stack-bounds!");
            System.out.println("Press \"ENTER\" to continue.");
            scanner.nextLine();
            scanner.nextLine();
            return;
        }
        // already four cards in deck
        if(this.profile.countCardsInDeck() == 4) {
            System.out.println("Deck is already full - remove some cards before adding new ones.");
            System.out.println("Press \"ENTER\" to continue.");
            scanner.nextLine();
            scanner.nextLine();
            return;
        }
        // adding card to deck
        this.profile.getStack().get(cardNumber - 1).setInDeck(true);
    }
    private void removeCardFromDeck()
    {
        Scanner scanner = new Scanner(System.in);
        // getting second user-input --> card-number to perform selected action
        System.out.print("Number of card: ");
        int cardNumber = scanner.nextInt();
        // when something goes wrong, we will return true to output an error message
        if(cardNumber < 1 || cardNumber > this.profile.getStack().size()) {
            System.out.println("Number out of stack-bounds!");
            System.out.println("Press \"ENTER\" to continue.");
            scanner.nextLine();
            scanner.nextLine();
            return;
        }
        // no cards to remove
        if(this.profile.countCardsInDeck() == 0) {
            System.out.println("Deck is empty - no cards can be removed.");
            System.out.println("Press \"ENTER\" to continue.");
            scanner.nextLine();
            scanner.nextLine();
            return;
        }
        // removing card from deck
        this.profile.getStack().get(cardNumber - 1).setInDeck(false);
    }
    public void addCardsToStack(ArrayList<Card> cardsToBeAdded)
    {
        this.profile.appendPackage(cardsToBeAdded);
    }
    public void printStack()
    {
        System.out.println();
        System.out.println("Your Stack:");
        int i = 1;
        System.out.println("\tNo.\tisInDeck\tDamage\tElement-Type\tName");
        for(Card card : this.profile.getStack()) {
            System.out.println("\t" + i +
                    "\t" + card.isInDeck() +
                    "\t\t" + card.getDamage() +
                    "\t\t" + card.getElementType() +
                    "\t\t\t" + card.getName());
            i++;
        }
    }

}
