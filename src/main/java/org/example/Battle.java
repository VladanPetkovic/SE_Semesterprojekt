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

    }
    public void printInstructions()
    {

    }
    public void startOfBattle()
    {

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
