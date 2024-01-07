package org.example.frontend;

import lombok.Getter;
import lombok.Setter;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class Battle {
    private final User userOne;
    private final User userTwo;
    private int winner_id;
    private int looser_id;
    private ArrayList<String> log;
    boolean tie = false;
    final ElementType[][] effectiveMatrix = new ElementType[3][3];

    public Battle(User userOne, User userTwo) {
        setEffectiveMatrix();
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public void runBattle() {
        int roundsPlayed = 0;
        this.log = new ArrayList<String>();

        while(roundsPlayed < 100 && this.userOne.countCardsInDeck() > 0 && this.userTwo.countCardsInDeck() > 0) {
            roundsPlayed++;

            // choose random card from deck
            Card userOneCard = this.userOne.getRandomCardFromDeck();
            Card userTwoCard = this.userTwo.getRandomCardFromDeck();

            // compare the cards
            Card betterCard = getBetterCard(userOneCard, userTwoCard);

            // move card to the other player
            if(betterCard == null) {
                // both cards have the same damage value
                this.log.add(getRoundLog(this.userOne, userOneCard, userTwoCard, roundsPlayed, true));
            }
            else if(Objects.equals(userOneCard.getId(), betterCard.getId())) {
                // player one has the better card
                this.userOne.getStack().add(new Card(userTwoCard, true));
                this.userTwo.removeCard(userTwoCard.getId());
                this.log.add(getRoundLog(this.userOne, userOneCard, userTwoCard, roundsPlayed, false));
            } else {
                this.userTwo.getStack().add(new Card(userOneCard, true));
                this.userOne.removeCard(userOneCard.getId());
                this.log.add(getRoundLog(this.userTwo, userTwoCard, userOneCard, roundsPlayed, false));
            }
        }

        setWinnerLooser(roundsPlayed);
    }

    public Card getBetterCard(Card userOneCard, Card userTwoCard) {
        // pure monster fights
        if(userOneCard.isMonsterCard() && userTwoCard.isMonsterCard()) {
            return monsterFight(userOneCard, userTwoCard);
        } else {
            // pure spell and mixed fights
            return spellMixFight(userOneCard, userTwoCard);
        }
    }

    public Card monsterFight(Card userOneCard, Card userTwoCard) {
        MonsterType cardOneType = userOneCard.getMonsterType();
        MonsterType cardTwoType = userTwoCard.getMonsterType();

        // check edge cases
        // Goblins are too afraid of Dragons to attack.
        if(cardOneType == MonsterType.Goblin && Objects.equals(userTwoCard.getName(), "Dragon")
        || cardTwoType == MonsterType.Goblin && Objects.equals(userOneCard.getName(), "Dragon")) {
            if(Objects.equals(userOneCard.getName(), "Dragon")) {
                return userOneCard;
            }
            return userTwoCard;
        }

        // Wizzard can control Orks so they are not able to damage them.
        if(Objects.equals(userOneCard.getName(), "Wizzard") && Objects.equals(userTwoCard.getName(), "Ork")
        || Objects.equals(userTwoCard.getName(), "Wizzard") && Objects.equals(userOneCard.getName(), "Ork")) {
            if(Objects.equals(userOneCard.getName(), "Wizzard")) {
                return userOneCard;
            }
            return userTwoCard;
        }

        // The FireElves know Dragons since they were little and can evade their attacks.
        if(Objects.equals(userOneCard.getName(), "FireElf") && Objects.equals(userTwoCard.getName(), "Dragon")
        || Objects.equals(userTwoCard.getName(), "FireElf") && Objects.equals(userOneCard.getName(), "Dragon")) {
            if(Objects.equals(userOneCard.getName(), "FireElf")) {
                return userOneCard;
            }
            return userTwoCard;
        }

        // all other:
        return compareDamage(userOneCard.getDamage(), userTwoCard.getDamage(), userOneCard, userTwoCard);
    }

    public Card spellMixFight(Card userOneCard, Card userTwoCard) {
        MonsterType cardOneType = userOneCard.getMonsterType();
        MonsterType cardTwoType = userTwoCard.getMonsterType();
        float userOneDamage = userOneCard.getDamage();
        float userTwoDamage = userTwoCard.getDamage();
        ElementType cardOneElement = userOneCard.getElementType();
        ElementType cardTwoElement = userTwoCard.getElementType();

        // edge cases
        // The armor of Knights is so heavy that WaterSpells make them drown them instantly.
        if(Objects.equals(userOneCard.getName(), "WaterSpell") && Objects.equals(userTwoCard.getName(), "Knight")
        || Objects.equals(userTwoCard.getName(), "WaterSpell") && Objects.equals(userOneCard.getName(), "Knight")) {
            if(Objects.equals(userOneCard.getName(), "WaterSpell")) {
                return userOneCard;
            }
            return userTwoCard;
        }

        // The Kraken is immune against spells.
        if(Objects.equals(userOneCard.getName(), "Kraken") && cardTwoType == MonsterType.Spell
        || Objects.equals(userTwoCard.getName(), "Kraken") && cardOneType == MonsterType.Spell) {
            if(Objects.equals(userOneCard.getName(), "Kraken")) {
                return userOneCard;
            }
            return userTwoCard;
        }

        // regular cases
        // effective and not effective
            // we are using some math: effective matrix

        // case: water and fire card
        if(this.effectiveMatrix[cardOneElement.ordinal()][cardTwoElement.ordinal()] == ElementType.WATER) {
            if(cardOneElement == ElementType.WATER) {
                return compareDamage(userOneDamage*2, userTwoDamage/2, userOneCard, userTwoCard);
            } else {
                return compareDamage(userOneDamage/2, userTwoDamage*2, userOneCard, userTwoCard);
            }
        }

        // case: fire and normal card
        if(this.effectiveMatrix[cardOneElement.ordinal()][cardTwoElement.ordinal()] == ElementType.FIRE) {
            if(cardOneElement == ElementType.FIRE) {
                return compareDamage(userOneDamage*2, userTwoDamage/2, userOneCard, userTwoCard);
            } else {
                return compareDamage(userOneDamage/2, userTwoDamage*2, userOneCard, userTwoCard);
            }
        }

        // case: normal and water card
        if(this.effectiveMatrix[cardOneElement.ordinal()][cardTwoElement.ordinal()] == ElementType.REGULAR) {
            if(cardOneElement == ElementType.REGULAR) {
                return compareDamage(userOneDamage*2, userTwoDamage/2, userOneCard, userTwoCard);
            } else {
                return compareDamage(userOneDamage/2, userTwoDamage*2, userOneCard, userTwoCard);
            }
        }

        // no effect
        return compareDamage(userOneDamage, userTwoDamage, userOneCard, userTwoCard);
    }

    public Card compareDamage(float userOneDamage, float userTwoDamage, Card userOneCard, Card userTwoCard) {
        if(userOneDamage == userTwoDamage) {            // tie
            return null;
        } else if(userOneDamage > userTwoDamage) {      // userOne wins round
            return userOneCard;
        } else {                                        // userTwo wins round
            return userTwoCard;
        }
    }

    public String getRoundLog(User winner, Card winner_card, Card looser_card, int roundNumber, Boolean isTie) {
        if(isTie) {
            return "\nRound-number: " + roundNumber + ".\t " + " Tie: equal damage values: " +
                    winner_card.getName() + ", Damage: " +
                    winner_card.getDamage() + ", Element-Type: " + winner_card.getElementType().toString() +
                    " against: " + looser_card.getName() + ", Damage: " +
                    looser_card.getDamage() + ", Element-Type: " + looser_card.getElementType().toString();
        }

        return "\nRound-number: " + roundNumber + ".\t " + winner.getProfile().getUsername() +
                " wins this round with: " + winner_card.getName() + ", Damage: " +
                winner_card.getDamage() + ", Element-Type: " + winner_card.getElementType().toString() +
                " against: " + looser_card.getName() + ", Damage: " +
                looser_card.getDamage() + ", Element-Type: " + looser_card.getElementType().toString();
    }

    public void setNewEloPoints(int userOneElo, int userTwoElo, Result result) {
        // the following Elo calculation is the official Elo calculation used in chess
        // the Result-parameter relates always to the userOneElo

        // if someone's elo number is 200 higher than yours, the person has a probability of 91% to win

        double calculationOne = 1/(1 + Math.pow(10, (double) (userTwoElo - userOneElo) /200));
        double calculationTwo = 1/(1 + Math.pow(10, (double) (userOneElo - userTwoElo) /200));
        int newEloUserOne = 0;
        int newEloUserTwo = 0;
        if(result == Result.LOSS) {
            newEloUserOne = (int) (userOneElo + 40 * (0 - calculationOne));
            newEloUserTwo = (int) (userTwoElo + 40 * (1 - calculationTwo));
        } else if(result == Result.WIN) {
            newEloUserOne = (int) (userOneElo + 40 * (1 - calculationOne));
            newEloUserTwo = (int) (userTwoElo + 40 * (0 - calculationTwo));
        } else if(result == Result.TIE) {
            newEloUserOne = (int) (userOneElo + 40 * (0.5 - calculationOne));
            newEloUserTwo = (int) (userTwoElo + 40 * (0.5 - calculationTwo));
        }

        this.userOne.getProfile().setEloPoints(newEloUserOne);
        this.userTwo.getProfile().setEloPoints(newEloUserTwo);
    }

    public void setWinnerLooser(int roundsPlayed) {
        int userOneElo = this.userOne.getProfile().getEloPoints();
        int userTwoElo = this.userTwo.getProfile().getEloPoints();

        if(roundsPlayed >= 100) {
            setTie(true);
            setNewEloPoints(userOneElo, userTwoElo, Result.TIE);    // tie
            setWinner_id(this.userOne.getProfile().getUser_id());
            setLooser_id(this.userTwo.getProfile().getUser_id());
        } else if(this.userOne.countCardsInDeck() == 0) {
            setNewEloPoints(userOneElo, userTwoElo, Result.LOSS);   // userTwo wins
            setWinner_id(this.userTwo.getProfile().getUser_id());
            setLooser_id(this.userOne.getProfile().getUser_id());
        } else {
            setNewEloPoints(userOneElo, userTwoElo, Result.WIN);    // userOne wins
            setWinner_id(this.userOne.getProfile().getUser_id());
            setLooser_id(this.userTwo.getProfile().getUser_id());
        }

    }

    /*            | a | b | c
               ---|---|---|---
                a | - | a | c
                b | a | - | b
                c | c | b | -

            a = water
            b = fire
            c = regular*/

    public void setEffectiveMatrix() {
        this.effectiveMatrix[0][0] = ElementType.NONE;
        this.effectiveMatrix[0][1] = ElementType.WATER;
        this.effectiveMatrix[0][2] = ElementType.REGULAR;

        this.effectiveMatrix[1][0] = ElementType.WATER;
        this.effectiveMatrix[1][1] = ElementType.NONE;
        this.effectiveMatrix[1][2] = ElementType.FIRE;

        this.effectiveMatrix[2][0] = ElementType.REGULAR;
        this.effectiveMatrix[2][1] = ElementType.FIRE;
        this.effectiveMatrix[2][2] = ElementType.NONE;
    }
}
