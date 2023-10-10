package org.example.game;

import org.example.game.Battle;
import org.example.game.Statistics;
import org.example.game.User;

import java.util.Scanner;

public class Game {
    public Game()
    {

    }

    private User[] users;
    private Statistics stats;


    public void startGame()
    {
        printWelcomeImage();
        stats = new Statistics();
        clearScreen();
    }
    public void showMenu()
    {
        String input = "";
        Scanner scanner = new Scanner(System.in);

        // choose different actions in the game menu
        do
        {
            printInstructions();
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("battle")) {
                startBattle();
            } else if (input.equalsIgnoreCase("stats")) {
                stats.printGameStats();
            } else if(input.equalsIgnoreCase("profile")) {
                System.out.println("Not implemented yet");
            } else {
                System.out.println("\tWrong input - choose again!");
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
        printAsciiImage();
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
    public void printWelcomeImage()
    {
        System.out.println("""
                 \t __  __\t\t________\t  ______  \t  ______
                 \t|MM\\/MM|\t|TTTTTT|\t /CCCCC|\t /GGGGG|
                 \t|M\\MM/M|\t  |TT|\t\t|C|     \t|G|
                 \t|M|\\/|M|\t  |TT|\t\t|C|     \t|G|  _/\\
                 \t|M|  |M|\t  |TT|\t\t|C|____  \t|G|___)G|
                 \t|M|  |M|\t  |TT|\t\t \\CCCCC|\t \\GGGGG/
                """);
        System.out.println("Welcome to Monster Trading Cards Game!");
        System.out.println();
    }
    public void printInstructions()
    {
        for(int i = 0; i < 4; i++) {
            System.out.println();
        }
        System.out.println("Game-Menu: ");
        System.out.println("\tChoose between following possibilities:");
        System.out.println("\t\tType \"BATTLE\" for battling against an other player.");
        System.out.println("\t\tType \"STATS\" for viewing your statistics.");
        System.out.println("\t\tType \"PROFILE\" for viewing and editing your profile.");
        System.out.println("\t\tType \"QUIT\" for quiting the game.");
        System.out.println("\tYour input can be case insensitive.");
        System.out.print("\t\tType here: ");
    }
    public static void clearScreen() {
        // does not work as intended, but does the job of clearing the screen
        System.out.println("Press -ENTER- to continue!");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        for(int i = 0; i < 4; i++) {
            System.out.println();
        }
        //scanner.close(); -- makes an error if uncommented
    }
}
