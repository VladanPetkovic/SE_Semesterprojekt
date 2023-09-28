package org.example;

import lombok.Getter;
import lombok.Setter;
import java.lang.Math;

@Getter
@Setter
public class Battle {
    private User userOne;
    private User userTwo;
    public void setUser()
    {

    };
    public User getUserOne()
    {
        return this.userOne;
    }
    public User getUserTwo()
    {
        return this.userTwo;
    }
    public void printInstructions()
    {

    }
    public void printAsciiImage()
    {

    }
    public void startOfBattle()
    {

    }
    public void endOfBattle()
    {

    }
    public void calculateEloPoints(int firstEloPoints, int secondEloPoints)
    {
        double calculation = 1/(1 + Math.pow(10, (double) (firstEloPoints - secondEloPoints) /200));
        System.out.println(calculation);
    }
}
