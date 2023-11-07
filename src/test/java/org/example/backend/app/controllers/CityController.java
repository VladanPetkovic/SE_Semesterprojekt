package org.example.backend.app.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CityController {
    @BeforeEach
    void beforeEach() {
        System.out.println("Running default before each test");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Running default after each test");
    }


    @Test
    void showCaseTest() {
        int a = 1;
        int b = 2;
        int expectedResult = 3;

        int actualResult = a + b;

        assertEquals(expectedResult, actualResult);
    }
}
