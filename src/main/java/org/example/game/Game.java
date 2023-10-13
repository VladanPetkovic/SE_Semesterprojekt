package org.example.game;

import org.example.game.Battle;
import org.example.game.Statistics;
import org.example.game.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public Game()
    {

    }

    private ArrayList<User> users = new ArrayList<>();
    private Statistics stats;
    private Shop shop;


    public void startGame()
    {
        printWelcomeImage();
        this.stats = new Statistics();
        this.shop = new Shop();
        clearScreen();
    }
    public void showMenu()
    {
        boolean wrongInputMade = false;
        String input;
        Scanner scanner = new Scanner(System.in);

        // choose different actions in the game menu
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
            if(input.equalsIgnoreCase("battle")) {
                startBattle();
            } else if(input.equalsIgnoreCase("shop")) {
                this.shop.shopMenu();
            }else if (input.equalsIgnoreCase("stats")) {
                this.stats.printGameStats();
            } else if(input.equalsIgnoreCase("profile")) {
                viewEditProfile();
            } else if(!input.equalsIgnoreCase("quit")){
                wrongInputMade = true;
            }
        }while(!input.equalsIgnoreCase("quit"));

        scanner.close();
    }
    public void startBattle()
    {
        Battle someBattle = new Battle();
        someBattle.setUserOne(users.get(0));
        someBattle.setUserTwo(users.get(1));
        someBattle.showBattleMenu();
    }
    public void viewEditProfile()
    {
        users.get(0).getProfile().printProfile();
        // editing to be done
    }
    public void endGame()
    {
        printAsciiImage();
        System.out.println("Thank you for playing!");
    }
    public void signIn()
    {
        String input;
        Scanner scanner = new Scanner(System.in);
        boolean isLoggedIn = false;

        System.out.println("\tProceed to login:");
        System.out.println("\t\tType \"login\" to login into your account.");
        System.out.println("\t\tType \"signup\" to create a new account.");

        do {
            input = scanner.nextLine();

            if(input.equalsIgnoreCase("login")) {
                isLoggedIn = login();
            }else if(input.equalsIgnoreCase("signup")) {
                isLoggedIn = signUp();
            } else {
                System.out.println("\tWrong input - type again!");
            }
        }while(!isLoggedIn);
        System.out.println("You successfully logged in!");
    }
    public boolean signUp()
    {
        return false;
    }
    public boolean login()
    {
        System.out.println("Please login into your account!");
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;

        // choose different actions in the game menu
        do
        {
            System.out.print("Username: ");
            username = scanner.nextLine();
            System.out.print("Password: ");
            password = scanner.nextLine();
        }while(!username.equals("Vladan") && !password.equals("password"));
        User newUser = new User();
        User secondUser = new User();
        users.add(newUser);
        newUser.setProfile(new Profile(username, password));
        users.add(secondUser);
        secondUser.setProfile(new Profile("Stefan", password));
        return true;
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
    public void printInstructions(boolean wrongInputMade)
    {
        for(int i = 0; i < 4; i++) {
            System.out.println();
        }
        System.out.println("Game-Menu: ");
        System.out.println("\tChoose between following possibilities:");
        System.out.println("\t\tType \"battle\" for battling against an other player.");
        System.out.println("\t\tType \"shop\" for purchasing and trading cards.");
        System.out.println("\t\tType \"stats\" for viewing your statistics.");
        System.out.println("\t\tType \"profile\" for viewing and editing your profile.");
        System.out.println("\t\tType \"quit\" for quiting the game.");
        System.out.println("\tYour input can be case insensitive.");
        if(wrongInputMade) {
            System.out.println("\t-----Wrong input - choose again!-----");
        }
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
