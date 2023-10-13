package org.example.game;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Profile {
    private String username;
    private String password;
    private int coins = 20;
    private int eloPoints = 100;
    private ArrayList<Card> stack = new ArrayList<>();

    Profile(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    public void printProfile()
    {
        System.out.println("Your Profile:");
        System.out.println("\tUsername: " + this.username);
        System.out.println("\tPassword: " + this.password);
        System.out.println("\tCoins: " + this.coins);
        System.out.println("\tElo-Points: " + this.eloPoints);
    }
}
