package org.example.backend.app.controllers;

import org.example.backend.app.models.User;
import org.example.backend.app.models.UserCredentials;
import org.example.backend.app.models.UserData;
import org.example.backend.app.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.server.Response;

import java.util.List;

public class UserController extends Controller {
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        setUserRepository(userRepository);
    }

    // for testing purpose
    public Response getUsers() {
        try {
            List userData = getUserRepository().getAll();
            String userDataJSON = getObjectMapper().writeValueAsString(userData);

            return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + userDataJSON + ", \"error\": null }"
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \" data\":null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public Response getUserByName(String name) {
        try {
            User user = getUserRepository().get(name);

            if(user != null) {
                UserData responseUser = new UserData(user);
                String userDataJSON = getObjectMapper().writeValueAsString(responseUser);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"data\": " + userDataJSON + ", \"error\": null }"
                );
            } else {
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \" data\":null, \"error\": \"User not found\" }"
                );
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \" data\":null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public Response updateUser(String body, String oldUsername) {
        try {
            // check, if oldUsername in db
            User user = getUserRepository().get(oldUsername);

            // check, if new username not in db
            UserData newUserData = getObjectMapper().readValue(body, UserData.class);
            User checkUser = getUserRepository().get(newUserData.getName());

            if(user != null && checkUser == null) {
                getUserRepository().update(user, newUserData);
                System.out.println("Updating user.");

                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ \"data\":null, \" error\":null }"
                );
            } else {
                System.out.println("User not found for updating.");
                return new Response(
                        HttpStatus.NOT_FOUND,
                        ContentType.JSON,
                        "{ \" data\":null, \"error\": \"User not found.\" }"
                );
            }
        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \" data\":null, \"error\": \"Internal Server Error\" }"
            );
        }
    }

    public Response createUser(String body) {
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
                        "{ \"data\":null, \" error\":null }"
                );
            } else {
                System.out.println("User already exists.");
                return new Response(
                        HttpStatus.CONFLICT,
                        ContentType.JSON,
                        "{ \" data\":null, \"error\": \"User with same username already registered\" }"
                );
            }
        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{ \" data\":null, \"error\": \"Internal Server Error\" }"
            );
        }
    }
}
