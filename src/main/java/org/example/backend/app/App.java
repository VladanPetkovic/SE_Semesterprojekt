package org.example.backend.app;

import org.example.backend.app.controllers.CardController;
import org.example.backend.app.controllers.UserController;
import org.example.backend.app.repository.CardRepository;
import org.example.backend.app.repository.UserRepository;
import org.example.backend.app.services.DatabaseService;
import org.example.backend.daos.CardDAO;
import org.example.backend.daos.UserDAO;
import org.example.backend.http.Authorization;
import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import lombok.AccessLevel;
import lombok.Setter;
import org.example.backend.server.Request;
import org.example.backend.server.Response;
import org.example.backend.server.ServerApp;
import org.example.frontend.Game;


public class App implements ServerApp {
    @Setter(AccessLevel.PRIVATE)
    private DatabaseService databaseService;
    @Setter(AccessLevel.PRIVATE)
    private UserController userController;
    @Setter(AccessLevel.PRIVATE)
    private CardController cardController;
    @Setter(AccessLevel.PRIVATE)
    private Game game;

    public App() {
        // init database-service
        this.databaseService = new DatabaseService();

        // init game
        setGame(new Game());

        // init user controller
        UserDAO userDao = new UserDAO(databaseService.getConnection());
        UserRepository userRepository = new UserRepository(userDao);
        setUserController(new UserController(userRepository, this.game));

        // init card controller
        CardDAO cardDAO = new CardDAO(databaseService.getConnection());
        CardRepository cardRepository = new CardRepository(cardDAO);
        setCardController(new CardController(cardRepository, this.game));

    }

    public Response handleRequest(Request request) {
        switch (request.getMethod()) {
            case GET: {
                String path = request.getPathname();
                if(path.matches("/users/\\w+")) {
                    String passedUsername = path.substring("/users/".length());
                    String token = request.getAuthorization();

                    if(passedUsername.isEmpty()) {
                        break;
                    }
                    return this.userController.getUserByName(passedUsername, token);
                } else if(path.equals("/cards")) {
                    String token = request.getAuthorization();

                    return this.cardController.getCards(token, this.databaseService);
                } else if(path.equals("/deck")) {
                    String token = request.getAuthorization();
                    // plain/json ignored for now

                    return this.cardController.getDeck(token, this.databaseService);
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
                } else if(request.getPathname().equals("/packages")) {
                    String body = request.getBody();
                    String token = request.getAuthorization();

                    if(body.isEmpty()) {
                        break;
                    }
                    return this.cardController.createPackage(body, token);
                } else if(request.getPathname().equals("/transactions/packages")) {
                    String token = request.getAuthorization();

                    return this.cardController.acquirePackage(token, this.databaseService);
                }
            }
            case PUT: {
                if (request.getPathname().matches("/users/\\w+")) {
                    String body = request.getBody();
                    String passedUsername = request.getPathname().substring("/users/".length());
                    String token = request.getAuthorization();

                    if(passedUsername.isEmpty() || body.isEmpty()) {
                        break;
                    }
                    return this.userController.updateUser(body, passedUsername, token);
                } else if(request.getPathname().equals("/deck")) {
                    String body = request.getBody();
                    String token = request.getAuthorization();

                    if(body.isEmpty()) {
                        break;
                    }
                    return this.cardController.updateDeck(token, body, this.databaseService);
                }
            }
        }

        return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{ \"error\": \"Not Found\", \"data\": null }");
    }
}
