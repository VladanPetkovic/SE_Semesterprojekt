package org.example;

import org.example.game.Game;
import org.example.server.app.App;
import org.example.server.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args)
    {
        /*
        Game game = new Game();
        game.startGame();
        game.showMenu();
        game.endGame();
        */


        App app = new App();
        Server server = new Server(app, 7777);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}