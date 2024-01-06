package org.example.backend.app.controllers;

import org.example.backend.app.models.User;
import org.example.backend.app.models.UserCredentials;
import org.example.backend.app.models.UserData;
import org.example.backend.app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.backend.http.Authorization;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.server.Response;
import org.example.frontend.Game;

public class UserController extends Controller {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private UserRepository userRepository;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Game game;

    public UserController(UserRepository userRepository, Game game) {
        setUserRepository(userRepository);
        setGame(game);
    }

    public Response getUserByName(String name, String token) {
        try {
            User user = getUserRepository().get(name);

            if(!checkAuthorization(token)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or invalid\" }"
                );
            }

            if(user != null) {
                UserData responseUser = new UserData(user);
                String userDataJSON = getObjectMapper().writeValueAsString(responseUser);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        token,
                        "{ \"data\": " + userDataJSON + ", \"error\": null }"
                );
            } else {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"User not found\" }"
                );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"data\": null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public synchronized Response updateUser(String body, String oldUsername, String token) {
        try {
            // check, if user has privileges
            if(!checkAuthorization(token)) {
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"Access token is missing or invalid\" }"
                );
            }

            // check, if oldUsername in db
            User oldUser = getUserRepository().get(oldUsername);

            if(oldUser != null) {
                // update old user with new data
                // backend
                UserData newUserData = getObjectMapper().readValue(body, UserData.class);
                getUserRepository().update(oldUser, newUserData);
                // frontend
                this.game.getUser(token).getProfile().setUsername(newUserData.getName());
                System.out.println("Updating user.");

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": null }"
                );
            } else {
                System.out.println("User not found for updating.");
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        token,
                        "{ \"data\": null, \"error\": \"User not found.\" }"
                );
            }
        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"data\": null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public synchronized Response createUser(String body) {
        try {
            UserCredentials newUser = getObjectMapper().readValue(body, UserCredentials.class);
            // check, if user in db
            User checkUser = getUserRepository().get(newUser.getUsername());

            if(checkUser == null) {
                getUserRepository().add(new User(newUser));
                System.out.println("Creating new user.");
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": null }"
                );
            } else {
                System.out.println("User already exists.");
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ \"data\": null, \"error\": \"User with same username already registered\" }"
                );
            }
        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"data\": null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public synchronized Response loginUser(String body) {
        try {
            UserCredentials newUser = getObjectMapper().readValue(body, UserCredentials.class);
            // check, if username and password correct
            User checkUser = getUserRepository().get(newUser.getUsername(), newUser.getPassword());

            if(checkUser != null) {
                System.out.println("User Login successful.");
                getGame().addUserToGame(checkUser);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        new Authorization().getAuth(newUser.getUsername()),
                        "{ \"data\":null, \"error\":null }"
                );
            } else {
                System.out.println("Invalid username/password provided.");
                return new Response(
                        HttpStatus.UNAUTHORIZED_ERROR,
                        ContentType.JSON,
                        "{ \"data\":null, \"error\": \"Invalid username/password provided\" }"
                );
            }
        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \"data\":null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public boolean checkAuthorization(String token) {
        return getGame().checkPlayerToken(token, false);
    }

    public int getUserMoney(String username) {
        return 0;
    }
}
