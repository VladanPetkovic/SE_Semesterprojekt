package org.example;

import java.util.Scanner;

public class Game {
    public Game()
    {

    }

    private User[] users;
    private Statistics stats;


    public void startGame()
    {
        printAsciiImage();
        clearScreen();
    }
    public void showMenu()
    {
        String input;
        printInstructions();
        Scanner scanner = new Scanner(System.in);

        // choose different actions in the game menu
        do
        {
            System.out.print("Type here: ");
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("battle"))
            {
                startBattle();
            } else if (input.equalsIgnoreCase("stats")) {
                stats.printGameStats();
            } else if(input.equalsIgnoreCase("profile")) {
                System.out.println("Not implemented yet");
            }
        }while(!input.equalsIgnoreCase("quit"));

        scanner.close();
    }
    public void startBattle()
    {
        Battle someBattle = new Battle();
        someBattle.showBattleMenu();
    }
    public void endGame()
    {
        System.out.println("Thank you for playing!");
    }
    public boolean register()
    {
        return false;
    }
    public boolean login()
    {
        return false;
    }
    public void printAsciiImage()
    {
        System.out.println("Welcome to Monster Trading Cards Game!");
        // clearing screen
        for(int i = 0; i < 4; i++)
        {
            System.out.println();
        }
        // ascii image from https://asciiart.website/index.php?art=creatures/monsters [29.09.2023].
        // credits to Shelia (aka "Melody")
        System.out.println(
                """
                                   / \\ / \\                       |:.     %|:.               |
                                   / ;\\~"`.       ,--------.     |:.     %|:.               |
                                  ( /  ) / \\      | *ULP*  |     |:.     %|:.               |
                                /~/     /#\\ `.     "~~\\/~~~"     |:.     %|:.               |
                               /__|     "~" /~\\/~]               |:.     %|:.               |
                               `/~~`\\       \\@  @                |:.     %|:.               |
                               /_____\\    ;-.,_,/                |:.     %|:.               |
                               ` /~~~~|   -.___/                 |:.     %|:.               |
                                /____.--.   \\-.                 /:.     %/:.                "
                                `/~~~|   \\   \\|        .____.~~:.____.~~:.                  \\
                                /____|        \\      /":.   %%/":.                           \\
                                `  ,,!    /    \\    /:. /:. %/:. /:.                          \\
                                   \\;\\   /~     '   |:. |:. %|:. |:.                           |
                                   / ~  /        '  |:. |:. %|:. |:.                          /
                                   \\___/        @ } `~~~^~~~-^~~~^~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                      \\          ,
                        """
        );
    }
    public void printInstructions()
    {
        System.out.println("Game-Menu: ");
        System.out.println("\tChoose between following possibilities:");
        System.out.println("\t\tType \"BATTLE\" for battling against an other player.");
        System.out.println("\t\tType \"STATS\" for viewing your statistics.");
        System.out.println("\t\tType \"PROFILE\" for viewing and editing your profile.");
        System.out.println("\t\tType \"QUIT\" for quiting the game.");
        System.out.println("\tYour input can be case insensitive.");
    }
    public static void clearScreen() {
        // does not work as intended, but does the job of clearing the screen
        System.out.println("Press -ENTER- to continue!");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        for(int i = 0; i < 8; i++)
        {
            System.out.println();
        }
        //scanner.close(); -- makes an error if uncommented
    }
}
