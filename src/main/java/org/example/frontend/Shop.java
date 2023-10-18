package org.example.frontend;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Shop {
    private static final int cardsInPackage = 5;
    private static final int priceForPackage = 5;
    public void purchaseCards(User customer)
    {
        // user has no money to afford one package of cards
        if(customer.getProfile().getCoins() < 5) {
            System.out.println("Not enough money available!");
            return;
        }

        // decreasing user-money
        customer.decreaseCoins(priceForPackage);
        System.out.println("You paid 5 coins for one package!");
        System.out.println("Your remaining coins: " + customer.getProfile().getCoins());

        // setting new random package to user stack
        customer.addCardsToStack(getRandomCards());
    }
    /**
     *  getRandomCards() return an ArrayList of five random cards
     *      random:
     *          - MonsterCard or SpellCard
     *          - damage-value
     *          - ElementType
     */
    public ArrayList<Card> getRandomCards()
    {
        ArrayList<Card> pack = new ArrayList<>();
        for(int i = 0; i < cardsInPackage; i++) {
            Card newCard;
            // get randomly a monster or spell card
            Random random = new Random();
            boolean isMonsterCard = random.nextBoolean();
            // get a random damage
            int randomDamage = random.nextInt(0,100 + 1);   // +1 needed to get value == 100
            int randomElement = random.nextInt(1,3 + 1);    // 1, 2, 3

            // init new Cards with random ElementType,  random damage, random CardType
            if(isMonsterCard) {
                newCard = new CardMonster(randomDamage, getElementType(randomElement));
            } else {
                newCard = new CardSpell(randomDamage, getElementType(randomElement));
            }

            // adding to the package of cards
            pack.add(newCard);
        }
        return pack;
    }
    private ElementType getElementType(int randomElement)
    {
        if(randomElement == 1) {
            return ElementType.FIRE;
        } else if(randomElement == 2) {
            return ElementType.WATER;
        } else {
            return ElementType.NORMAL;
        }
    }
    public void tradeCards(Card cardToBeRemoved)
    {

    }
    public void shopMenu(User userOne, User userTwo)
    {
        boolean wrongInputMade = false;
        Scanner scanner = new Scanner(System.in);
        String input;

        // choose different actions in game
        do
        {
            // print Instructions to console
            if(wrongInputMade) {
                printInstructions(wrongInputMade);
                wrongInputMade = false;
            } else {
                printInstructions(wrongInputMade);
            }
            // get Input
            input = scanner.nextLine();
            // process the input
            if(input.equalsIgnoreCase("package")) {
                purchaseCards(userOne);
                purchaseCards(userTwo);     // FOR TESTING --> TO BE DONE
            } else if(input.equalsIgnoreCase("trade")) {
                System.out.println("trading some cards");
            } else if(!input.equalsIgnoreCase("back")) {
                wrongInputMade = true;
            }
        }while(!input.equalsIgnoreCase("back"));
    }
    public void printInstructions(boolean wrongInputMade)
    {
        for(int i = 0; i < 4; i++) {
            System.out.println();
        }
        System.out.println("Shop-Menu: ");
        System.out.println("\t\tType \"package\" for buying a package.");
        System.out.println("\t\tType \"trade\" for trading cards with other players.");
        System.out.println("\t\tType \"back\" for returning to the main menu.");
        System.out.println("\tYour input can be case insensitive.");
        if(wrongInputMade) {
            System.out.println("\t-----Wrong input - choose again!-----");
        }
        System.out.print("\t\tType here: ");
    }

}
