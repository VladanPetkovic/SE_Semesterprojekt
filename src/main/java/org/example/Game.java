package org.example;

import java.util.Scanner;

public class Game {
    public Game()
    {

    }

    public User[] users;
    public Battle battle;
    public Statistics stats;


    public void startGame()
    {
        printAsciiImage();
        clearScreen();
    }
    public void chooseAction()
    {

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
        System.out.println("Some Instructions:");
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
    }
}
