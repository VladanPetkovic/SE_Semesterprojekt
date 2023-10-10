package org.example.game;

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

        System.out.println("Battle-logic:");
        System.out.println("\tYour items:");
        System.out.println("\t\tSTACK");
        System.out.println("\t\t\tThe collections of your cards - you can trade cards.");
        System.out.println("\t\tDECK");
        System.out.println("\t\t\tFour best cards you have selected for the battle.");
        System.out.println("\t\tCOINS");
        System.out.println("\t\t\tYou are starting with 20 coins.");
        System.out.println("\t\t\tBuy a package (5 cards) for 5 coins.");
        System.out.println("\tGame-logic:");
        System.out.println("\t\tCards:");
        System.out.println("\t\t\tMonster cards:");
        System.out.println("\t\t\t\tactive attack, damage based on element type (fire, water, normal)");
        System.out.println("\t\t\t\telement type does not effect pure monster fights");
        System.out.println("\t\t\tSpell cards:");
        System.out.println("\t\t\t\tattacks with an element based spell (fire, water, normal)");
        System.out.println("\t\t\t\t\teffective, non effective, no effect");
        System.out.println("\t\tSelect the best four cards into your deck");
        System.out.println("\tYour input can be case insensitive.");
  /*
• spell cards
        a spell card can attack with an element based spell (again fire, water, normal) which is:
– effective (eg: water is effective against fire, so damage is doubled)
– not effective (eg: fire is not effective against water, so damage is halved)
– no effect (eg: normal monster vs normal spell, no change of damage, direct
        comparison between damages) Effectiveness:
• water -> fire
• fire -> normal
• normal -> water
        Cards are chosen randomly each round from the deck to compete (this means 1 round is a
            battle of 2 cards = 1 of each player). There is no attacker or defender. All parties are equal in
        each round. Pure monster fights are not affected by the element type. As soon as 1 spell
        cards is played the element type has an effect on the damage calculation of this single
        round. Each round the card with higher calculated damage wins. Defeated monsters/spells
        of the competitor are removed from the competitor’s deck and are taken over in the deck of
        the current player (vice versa). In case of a draw of a round no action takes place (no cards
        are moved). Because endless loops are possible we limit the count of rounds to 100 (ELO
        stays unchanged in case of a draw of the full game).
        3
        As a result of the battle we want to return a log which describes the battle in great detail.
        Afterwards the player stats (see scoreboard) need to be updated (count of games played
        and ELO calculation).
        The following specialties are to consider:
• Goblins are too afraid of Dragons to attack.
• Wizzard can control Orks so they are not able to damage them.
• The armor of Knights is so heavy that WaterSpells make them drown them instantly.
• The Kraken is immune against spells.
• The FireElves know Dragons since they were little and can evade their attacks.
*/
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
