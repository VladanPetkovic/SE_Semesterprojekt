package org.example.backend.app.controllers;

import org.example.backend.app.models.User;
import org.example.backend.app.services.UserService;
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
    private UserService userService;

    public UserController(UserService userService) {
        setUserService(userService);
    }

    // DELETE /users/:id -> deletes a user with the 'id'
    // POST /users -> makes a new user
    // PUT/PATCH /users/:id -> updates a user with the 'id'
    // GET /users/:id -> return one user with the 'id'
    // GET /users -> return all users
    public Response getUsers() {
        try {
            List userData = getUserService().getUsers();
            String cityDataJSON = getObjectMapper().writeValueAsString(userData);

            return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                "{ \"data\": " + cityDataJSON + ", \"error\": null }"
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"error\": \"Internal Server Error\", \"data\": null }"
            );
        }
    }

    // GET /users/:id
    public void getUserById(int id) {

    }

    // POST /users
    public Response createUser(String body) {
        try {
            System.out.println(body);
            User newUser = getObjectMapper().readValue(body, User.class);
            getUserService().addUser(newUser);
            return new Response(
                    HttpStatus.CREATED,
                    ContentType.JSON,
                    "{\"data\": " + body + ", \" error\":null }"
            );
        } catch(JsonProcessingException e)
        {
            e.printStackTrace();
            return new Response(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ContentType.JSON,
                    "{\"error\": \"Internal Server Error\", \" error\":null }"
            );
        }

    }

    // DELETE /users/:id
    public void deleteUser(int id) {

    }
}
