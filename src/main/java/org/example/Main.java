package org.example;

public class Main {
    public static void main(String[] args)
    {
        var newBattle = new Battle();
        newBattle.calculateEloPoints(100, 100);
        newBattle.calculateEloPoints(100, 300);
        newBattle.calculateEloPoints(100, 200);
        newBattle.calculateEloPoints(100, 166);
    }
}