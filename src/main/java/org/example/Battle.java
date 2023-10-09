package org.example;

import lombok.Getter;
import lombok.Setter;
import java.lang.Math;
import java.util.Scanner;

@Getter
@Setter
public class Battle {
    private User userOne;
    private User userTwo;
    public void setUser()
    {

    }
    public void printInstructions()
    {
        for(int i = 0; i < 5; i++)
            System.out.println();

        System.out.println("Instructions: ");
        System.out.println("\t\tType \"START\" for starting a new battle.");
        System.out.println("\t\tType \"INFO\" for viewing the battle instructions - how the game is played.");
        System.out.println("\t\tType \"BACK\" for returning to the main menu.");
        System.out.println("\tYour input can be case insensitive.");
    }
    public void printBattleInformation()
    {
        for(int i = 0; i < 5; i++)
            System.out.println();

        System.out.println("Battle-logic: ");
        System.out.println("\tYour items:");
        System.out.println("\t\tSTACK");
        System.out.println("\t\t\tThe collections of your cards - you can trade cards.");
        System.out.println("\t\tDECK");
        System.out.println("\t\t\tFour best cards you have selected for the battle.");
        System.out.println("\t\tCOINS");
        System.out.println("\t\t\tYou are starting with 20 coins.");
        System.out.println("\t\t\tBuy a package (5 cards) for 5 coins.");
        System.out.println("\t\tCard is either a spell or a monster card.");
        System.out.println("\t\tCard is either a spell or a monster card.");
        System.out.println("\t\tYou can buy a package (5 cards) with 5 coins.");
        System.out.println("\t\tSelect the best four cards into your deck");
        System.out.println("\tYour input can be case insensitive.");
    }
    public void showBattleMenu()
    {
        printInstructions();
        Scanner scanner = new Scanner(System.in);
        String input;

        // choose different actions in game
        do
        {
            System.out.print("Type here: ");
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("start"))
            {
                startOfBattle();
            } else if (input.equalsIgnoreCase("info")) {
                printBattleInformation();
            }
        }while(!input.equalsIgnoreCase("back"));

    }
    public void startOfBattle()
    {
        System.out.println("Some battle start");
    }
    public void endOfBattle()
    {

    }
    public void setNewEloPoints(int userOneElo, int userTwoElo, Result result)
    {
        // the following Elo calculation is the official Elo calculation used in chess
        // the Result-parameter relates always to the userOneElo

        double calculationOne = 1/(1 + Math.pow(10, (double) (userTwoElo - userOneElo) /200));
        double calculationTwo = 1/(1 + Math.pow(10, (double) (userOneElo - userTwoElo) /200));
        int newEloUserOne = 0;
        int newEloUserTwo = 0;
        if(result == Result.LOSS)
        {
            newEloUserOne = (int) (userOneElo + 40 * (0 - calculationOne));
            newEloUserTwo = (int) (userTwoElo + 40 * (1 - calculationTwo));
        }
        else if(result == Result.WIN)
        {
            newEloUserOne = (int) (userOneElo + 40 * (1 - calculationOne));
            newEloUserTwo = (int) (userTwoElo + 40 * (0 - calculationTwo));
        }
        else if(result == Result.TIE)
        {
            newEloUserOne = (int) (userOneElo + 40 * (0.5 - calculationOne));
            newEloUserTwo = (int) (userTwoElo + 40 * (0.5 - calculationTwo));
        }
        //System.out.println("User Two: " + newEloUserTwo);
        //System.out.println("User One: " + newEloUserOne);

        getUserOne().getProfile().setEloPoints(newEloUserOne);
        getUserOne().getProfile().setEloPoints(newEloUserTwo);
    }
}
