package org.example.frontend;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardTests {
    @BeforeAll
    void beforeAll() {
        System.out.println("Starting with Card-tests");
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
        System.out.println("Card-tests finished");
    }
    @Test
    void setMonsterType_CheckInitialization() {
        System.out.println("Check: Initialization of the property Monstertype");

        // arrange
        Card checkCard = new Card("FireTroll", 77);
        MonsterType monsterType = checkCard.getMonsterType();
        MonsterType expectedValue = MonsterType.Troll;

        // act
        // was done in initialization of the card

        // assert
        assertEquals(expectedValue, monsterType);
    }

    @Test
    void setElementType_CheckInitialization() {
        System.out.println("Check: Initialization of the property ElementType");

        // arrange
        Card checkCard = new Card("Wizzard", 50);
        ElementType elementType = checkCard.getElementType();
        ElementType expectedValue = ElementType.REGULAR;

        // act
        // was done in initialization of the card

        // assert
        assertEquals(expectedValue, elementType);
    }

}
