package org.example.frontend;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameTests {
    Game game;

    @BeforeAll
    void beforeAll() {
        System.out.println("Starting with Game-tests");
        this.game = new Game();
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
        System.out.println("Game-tests finished");
    }

    @Test
    void checkPlayerToken_ScenarioValidNotAdmin() {
        System.out.println("Check: Valid player-token passed, not admin");
        // Arrange
        User checkUser = new User();
        checkUser.getProfile().setToken("Bearer vladan-mtcgToken");
        this.game.getUsers().add(checkUser);
        boolean expectedValue = true;

        // Act
        boolean actualValue = this.game.checkPlayerToken(checkUser.getProfile().getToken(), false);

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void checkPlayerToken_ScenarioValidAdmin() {
        System.out.println("Check: Valid admin-token passed, only admin privileges");
        // Arrange
        User checkUser = new User();
        checkUser.getProfile().setToken("Bearer admin-mtcgToken");
        this.game.getUsers().add(checkUser);
        boolean expectedValue = true;

        // Act
        boolean actualValue = this.game.checkPlayerToken(checkUser.getProfile().getToken(), true);

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void checkPlayerToken_ScenarioInvalidNotAdmin() {
        System.out.println("Check: Invalid player-token passed, not admin");
        // Arrange
        User checkUser = new User();
        checkUser.getProfile().setToken("Bearer vladan-mtcgToken");
        this.game.getUsers().add(checkUser);
        boolean expectedValue = false;

        // Act
        boolean actualValue = this.game.checkPlayerToken("Bearer tom-mtcgToken", false);

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void checkPlayerToken_ScenarioInvalidAdmin() {
        System.out.println("Check: Invalid admin-token passed, only admin privileges");
        // Arrange
        User checkUser = new User();
        checkUser.getProfile().setToken("Bearer admin-mtcgToken");
        this.game.getUsers().add(checkUser);
        boolean expectedValue = false;

        // Act
        boolean actualValue = this.game.checkPlayerToken("Bearer tom-mtcgToken", true);

        // Assert
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void checkPlayerToken_ScenarioValidAdminButWithoutPriv() {
        System.out.println("Check: Admin token passed, but without privileges");
        // Arrange
        User checkUser = new User();
        checkUser.getProfile().setToken("Bearer admin-mtcgToken");
        this.game.getUsers().add(checkUser);
        boolean expectedValue = true;

        // Act
        boolean actualValue = this.game.checkPlayerToken(checkUser.getProfile().getToken(), false);

        // Assert
        assertEquals(expectedValue, actualValue);
    }
}
