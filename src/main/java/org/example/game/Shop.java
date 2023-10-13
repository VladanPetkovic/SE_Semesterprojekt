package org.example.game;

import java.util.Scanner;

public class Shop {
    public void purchaseCards()
    {

    }
    public void tradeCards(Card cardToBeRemoved)
    {

    }
    public void shopMenu()
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
                System.out.println("buying some package");
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
