package org.example.frontend;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardTests {
    @BeforeAll
    void beforeAll() {
        System.out.println("Starting with Card tests");
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

    void getMonsterType_CheckInitialization() {
        System.out.println("Check: Initialization of the property Monstertype");

        // arrange
        //Card checkCard = new Card();
    }

}
