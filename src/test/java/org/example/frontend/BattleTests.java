package org.example.frontend;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BattleTests {
    Battle battle;
    User userOne;
    User userTwo;
    @BeforeAll
    void beforeAll() {
        // init users
        this.userOne = new User();
        this.userTwo = new User();
        // init battle
        this.battle = new Battle(this.userOne, this.userTwo);

        System.out.println("Starting with Battle tests");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("----------------------------------------------------------------------------");
    }

    @AfterEach
    void afterEach() {
        System.out.println("----------------------------------------------------------------------------");
    }

    @AfterAll
    void afterAll() {
        System.out.println("Battle tests finished");
    }

    @Test
    void compareDamage_scenarioEqual() {
        // scenario: equal cards --> return null
        System.out.println("Check: Equal damage-value --> no Card is better, return null");

        // arrange
        Card userOneCard = new Card("FireElf", 55);
        Card userTwoCard = new Card("FireElf", 55);

        // act
        Card actualResult = this.battle.compareDamage(
                userOneCard.getDamage(),
                userTwoCard.getDamage(),
                userOneCard,
                userTwoCard);

        // assert
        assertNull(actualResult);
    }

    @Test
    void compareDamage_scenarioUserOne() {
        System.out.println("Check: Not equal damage-value --> userOne-Card is better, return Card of userOne");

        // arrange
        Card userOneCard = new Card("FireElf", 56);
        Card userTwoCard = new Card("FireElf", 55);
        Card expectedResult = userOneCard;

        // act
        Card actualResult = this.battle.compareDamage(
                userOneCard.getDamage(),
                userTwoCard.getDamage(),
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void compareDamage_scenarioUserTwo() {
        System.out.println("Check: Not equal damage-value --> userTwo-Card is better, return Card of userTwo");

        // arrange
        Card userOneCard = new Card("FireElf", 56);
        Card userTwoCard = new Card("FireElf", 56.5F);
        Card expectedResult = userTwoCard;

        // act
        Card actualResult = this.battle.compareDamage(
                userOneCard.getDamage(),
                userTwoCard.getDamage(),
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void monsterFight_scenarioGoblinDragon() {
        System.out.println("Check: Goblin should loose against Dragons");

        // arrange
        Card userOneCard = new Card("WaterGoblin", 100);
        Card userTwoCard = new Card("Dragon", 55);
        Card expectedResult = userTwoCard;

        // act
        Card actualResult = this.battle.monsterFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void monsterFight_scenarioWizzardOrk() {
        System.out.println("Check: Wizzard control Orks");

        // arrange
        Card userOneCard = new Card("Wizzard", 35);
        Card userTwoCard = new Card("Ork", 100);
        Card expectedResult = userOneCard;

        // act
        Card actualResult = this.battle.monsterFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void monsterFight_scenarioDragonFireElves() {
        System.out.println("Check: FireElves win against Dragons");

        // arrange
        Card userOneCard = new Card("FireElf", 35);
        Card userTwoCard = new Card("Dragon", 100);
        Card expectedResult = userOneCard;

        // act
        Card actualResult = this.battle.monsterFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void monsterFight_scenarioTypicalMonsterFight() {
        System.out.println("Check: standard monster fight - compare damage-value");

        // arrange
        Card userOneCard = new Card("RegularGoblin", 75);
        Card userTwoCard = new Card("WaterElf", 100);
        Card expectedResult = userTwoCard;

        // act
        Card actualResult = this.battle.monsterFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void spellMixFight_scenarioKnightWaterSpell() {
        System.out.println("Check: Knights loose against WaterSpells");

        // arrange
        Card userOneCard = new Card("WaterSpell", 35);
        Card userTwoCard = new Card("Knight", 100);
        Card expectedResult = userOneCard;

        // act
        Card actualResult = this.battle.spellMixFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void spellMixFight_scenarioKrakenSpell() {
        System.out.println("Check: Kraken wins against spell-cards");

        // arrange
        Card userOneCard = new Card("Kraken", 35);
        Card userTwoCard = new Card("RegularSpell", 100);
        Card expectedResult = userOneCard;

        // act
        Card actualResult = this.battle.spellMixFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void spellMixFight_scenarioEffectiveWin() {
        System.out.println("Check: Case: fire and normal card --> fire card doubles damage, normal card halves");

        // arrange
        Card userOneCard = new Card("FireTroll", 35);
        Card userTwoCard = new Card("RegularSpell", 100);
        Card expectedResult = userOneCard;

        // act
        Card actualResult = this.battle.spellMixFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void spellMixFight_scenarioEffectiveWinMixedUsers() {
        System.out.println("Check: Case: fire and normal card --> fire card doubles damage, normal card halves --> users switched to previous test");

        // arrange
        Card userOneCard = new Card("RegularSpell", 100);
        Card userTwoCard = new Card("FireTroll", 26);
        Card expectedResult = userTwoCard;

        // act
        Card actualResult = this.battle.spellMixFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void spellMixFight_scenarioEffectiveTie() {
        System.out.println("Check: Case: Tie cause of damage doubling/halving");

        // arrange
        Card userOneCard = new Card("WaterSpell", 25);
        Card userTwoCard = new Card("FireTroll", 100);

        // act
        Card actualResult = this.battle.spellMixFight(
                userOneCard,
                userTwoCard);

        // assert
        assertNull(actualResult);
    }

    @Test
    void spellMixFight_scenarioEffectiveLoss() {
        System.out.println("Check: Not Effective Card wins, because damage-value is to high");

        // arrange
        Card userOneCard = new Card("RegularTroll", 24);
        Card userTwoCard = new Card("WaterTroll", 100);
        Card expectedResult = userTwoCard;

        // act
        Card actualResult = this.battle.spellMixFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void setNewEloPoints_scenarioTieSameELO() {
        System.out.println("Check: Elo should not change, when both players have the same elo");

        // arrange
        // test user without data
        // elo_points set to 100 default
        User userOne = new User();
        User userTwo = new User();
        int expectedEloValue = 100;

        // act
        this.battle.setNewEloPoints(
                userOne.getProfile().getEloPoints(),
                userTwo.getProfile().getEloPoints(),
                Result.TIE);

        // assert
        assertEquals(expectedEloValue, userOne.getProfile().getEloPoints());
        assertEquals(expectedEloValue, userTwo.getProfile().getEloPoints());
    }

    @Test
    void setNewEloPoints_scenarioTieDifferentELO() {
        System.out.println("Check: Elo should change, even if a tie results, because userOne has a higher elo-value");

        // arrange
        // test user without data
        // elo_points set to 100 default
        this.battle.getUserOne().getProfile().setEloPoints(300);
        this.battle.getUserTwo().getProfile().setEloPoints(100);

        // act
        this.battle.setNewEloPoints(
                userOne.getProfile().getEloPoints(),
                userTwo.getProfile().getEloPoints(),
                Result.TIE);

        // assert
        int userOneElo = this.battle.getUserOne().getProfile().getEloPoints();   // was 300
        int userTwoElo = this.battle.getUserTwo().getProfile().getEloPoints();   // was 100
        System.out.println("new ELO of userOne: " + userOneElo);
        System.out.println("new ELO of userTwo: " + userTwoElo);
        assertTrue(userOneElo < 300);
        assertTrue(userTwoElo > 100);
    }

    @Test
    void setNewEloPoints_scenarioWinSameELO() {
        System.out.println("Check: Winner should have a higher elo-value afterwards");

        // arrange
        // test user without data
        // elo_points set to 100 default
        this.battle.getUserOne().getProfile().setEloPoints(120);
        this.battle.getUserTwo().getProfile().setEloPoints(120);

        // act
        this.battle.setNewEloPoints(
                userOne.getProfile().getEloPoints(),
                userTwo.getProfile().getEloPoints(),
                Result.WIN);

        // assert
        int userOneElo = this.battle.getUserOne().getProfile().getEloPoints();   // was 120
        int userTwoElo = this.battle.getUserTwo().getProfile().getEloPoints();   // was 120
        System.out.println("new ELO of userOne: " + userOneElo);
        System.out.println("new ELO of userTwo: " + userTwoElo);
        assertTrue(userOneElo > 120);
        assertTrue(userTwoElo < 120);
    }

    @Test
    void setNewEloPoints_scenarioWinSameELOChangeUsers() {
        System.out.println("Check: Now userOne looses --> check, if different sequence works");

        // arrange
        // test user without data
        // elo_points set to 100 default
        this.battle.getUserOne().getProfile().setEloPoints(120);
        this.battle.getUserTwo().getProfile().setEloPoints(120);

        // act
        this.battle.setNewEloPoints(
                userOne.getProfile().getEloPoints(),
                userTwo.getProfile().getEloPoints(),
                Result.LOSS);

        // assert
        int userOneElo = this.battle.getUserOne().getProfile().getEloPoints();   // was 120
        int userTwoElo = this.battle.getUserTwo().getProfile().getEloPoints();   // was 120
        System.out.println("new ELO of userOne: " + userOneElo);
        System.out.println("new ELO of userTwo: " + userTwoElo);
        assertTrue(userOneElo < 120);
        assertTrue(userTwoElo > 120);
    }

    @Test
    void setNewEloPoints_scenarioWinDifferentELO() {
        System.out.println("Check: ELO-value changes properly for the winner, looser-elo shrinks");

        // arrange
        // test user without data
        // elo_points set to 100 default
        this.battle.getUserOne().getProfile().setEloPoints(190);
        this.battle.getUserTwo().getProfile().setEloPoints(170);

        // act
        this.battle.setNewEloPoints(
                userOne.getProfile().getEloPoints(),
                userTwo.getProfile().getEloPoints(),
                Result.LOSS);

        // assert
        int userOneElo = this.battle.getUserOne().getProfile().getEloPoints();   // was 190
        int userTwoElo = this.battle.getUserTwo().getProfile().getEloPoints();   // was 170
        System.out.println("new ELO of userOne: " + userOneElo);
        System.out.println("new ELO of userTwo: " + userTwoElo);
        assertTrue(userOneElo < 190);
        assertTrue(userTwoElo > 170);
    }
}
