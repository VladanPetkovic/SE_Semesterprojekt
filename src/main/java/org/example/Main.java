package org.example;

import org.example.backend.app.App;
import org.example.backend.server.Server;

import java.io.IOException;

public class
Main {
    public static void main(String[] args)
    {
        App app = new App();
        Server server = new Server(app, 10001);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}