package org.example.backend.app;

import org.example.backend.app.controllers.UserController;
import org.example.backend.app.repository.UserRepository;
import org.example.backend.app.services.DatabaseService;
import org.example.backend.daos.UserDao;
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
        DatabaseService databaseService = new DatabaseService();

        UserDao userDao = new UserDao(databaseService.getConnection());
        UserRepository userRepository = new UserRepository(userDao);

        setUserController(new UserController(userRepository));
    }

    public Response handleRequest(Request request) {


        switch (request.getMethod()) {
            case GET: {
                String path = request.getPathname();
                if (path.equals("/users")) {
                    return this.userController.getUsers();
                } else if(path.matches("/users/\\w+")) {
                    String passedUsername = path.substring("/users/".length());

                    if(passedUsername.isEmpty()) {
                        break;
                    }
                    return this.userController.getUserByName(passedUsername);
                }
            }
            case POST: {
                if (request.getPathname().equals("/users")) {
                    String body = request.getBody();

                    if(body.isEmpty()) {
                        break;
                    }
                    return this.userController.createUser(body);
                } else if(request.getPathname().equals("/sessions")) {
                    String body = request.getBody();

                    if(body.isEmpty()) {
                        break;
                    }
                    return this.userController.loginUser(body);
                }
            }
            case PUT: {
                if (request.getPathname().matches("/users/\\w+")) {
                    String body = request.getBody();
                    String passedUsername = request.getPathname().substring("/users/".length());
                    String token = request.getAuthorization();

                    if(passedUsername.isEmpty() || body.isEmpty() || token.isEmpty()) {
                        break;
                    }
                    return this.userController.updateUser(body, passedUsername, token);
                }
            }
        }

        return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{ \"error\": \"Not Found\", \"data\": null }");
    }
}
