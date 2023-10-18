package org.example.backend.app;

import org.example.backend.app.controllers.UserController;
import org.example.backend.app.services.UserService;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import lombok.AccessLevel;
import lombok.Setter;
import org.example.backend.server.Request;
import org.example.backend.server.Response;
import org.example.backend.server.ServerApp;


public class App implements ServerApp {
    @Setter(AccessLevel.PRIVATE)
    private UserController userController;

    public App() {
        setUserController(new UserController(new UserService()));
    }

    public Response handleRequest(Request request) {


        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().equals("/users")) {
                    return this.userController.getUsers();
                }
            }
            case POST: {
                if (request.getPathname().equals("/users")) {
                    String body = request.getBody();
                    return this.userController.createUser(body);
                }
            }
        }

        return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{ \"error\": \"Not Found\", \"data\": null }");
    }
}
