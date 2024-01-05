package org.example.backend.app;

import org.example.backend.app.controllers.BattleController;
import org.example.backend.app.controllers.CardController;
import org.example.backend.app.controllers.UserController;
import org.example.backend.app.repository.BattleRepository;
import org.example.backend.app.repository.CardRepository;
import org.example.backend.app.repository.UserRepository;
import org.example.backend.app.services.DatabaseService;
import org.example.backend.daos.BattleDAO;
import org.example.backend.daos.CardDAO;
import org.example.backend.daos.UserDAO;
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
    private BattleController battleController;
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

        // init game controller
        BattleDAO battleDAO = new BattleDAO((databaseService.getConnection()));
        BattleRepository battleRepository = new BattleRepository((battleDAO));
        setBattleController(new BattleController(battleRepository, this.game));
    }

    public Response handleRequest(Request request) {
        String token = request.getAuthorization();
        String body = request.getBody();

        switch (request.getMethod()) {
            case GET: {

                // just testing
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                String path = request.getPathname();
                if(path.matches("/users/\\w+")) {
                    String passedUsername = path.substring("/users/".length());

                    if(passedUsername.isEmpty()) {
                        break;
                    }
                    return this.userController.getUserByName(passedUsername, token);
                } else if(path.equals("/cards")) {
                    return this.cardController.getCards(token, this.databaseService);
                } else if(path.matches("/deck(\\?format=(json|plain))?")) {
                    // plain/json
                    String passedFormat = null;
                    if(request.getParams().length() >= 11) {
                        passedFormat = request.getParams().substring(7);
                    }

                    return this.cardController.getDeck(token, passedFormat, this.databaseService);
                } else if(path.equals("/stats")) {
                    return this.battleController.getStats(token, this.databaseService);
                } else if(path.equals("/scoreboard")) {
                    return this.battleController.getScoreboard(token, this.databaseService);
                }
            }
            case POST: {
                if (request.getPathname().equals("/users")) {
                    if(body.isEmpty()) {
                        break;
                    }
                    return this.userController.createUser(body);
                } else if(request.getPathname().equals("/sessions")) {
                    if(body.isEmpty()) {
                        break;
                    }
                    return this.userController.loginUser(body);
                } else if(request.getPathname().equals("/packages")) {
                    if(body.isEmpty()) {
                        break;
                    }
                    return this.cardController.createPackage(body, token);
                } else if(request.getPathname().equals("/transactions/packages")) {
                    return this.cardController.acquirePackage(token, this.databaseService);
                }
            }
            case PUT: {
                if (request.getPathname().matches("/users/\\w+")) {
                    String passedUsername = request.getPathname().substring("/users/".length());

                    if(passedUsername.isEmpty() || body.isEmpty()) {
                        break;
                    }
                    return this.userController.updateUser(body, passedUsername, token);
                } else if(request.getPathname().equals("/deck")) {
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
