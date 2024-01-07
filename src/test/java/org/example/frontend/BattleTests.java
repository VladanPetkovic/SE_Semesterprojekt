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
        Card userOneCard = new Card("FireElf", 55, ElementType.REGULAR);
        Card userTwoCard = new Card("FireElf", 55, ElementType.FIRE);

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
        Card userOneCard = new Card("FireElf", 56, ElementType.REGULAR);
        Card userTwoCard = new Card("FireElf", 55, ElementType.FIRE);
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
        Card userOneCard = new Card("FireElf", 56, ElementType.REGULAR);
        Card userTwoCard = new Card("FireElf", 56.5F, ElementType.FIRE);
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
        Card userOneCard = new Card("WaterGoblin", 100, ElementType.REGULAR);
        Card userTwoCard = new Card("Dragon", 55, ElementType.FIRE);
        Card expectedResult = userTwoCard;

        // act
        Card actualResult = this.battle.monsterFight(
                userOneCard,
                userTwoCard);

        // assert
        assertEquals(expectedResult, actualResult);
    }
}
