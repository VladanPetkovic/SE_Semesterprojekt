package org.example.backend.server;

import org.example.backend.app.App;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.*;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private App app;
    private int port;

    public Server(App app, int port) {
        setApp(app);
        setPort(port);
    }

    public void start() throws IOException {
        setServerSocket(new ServerSocket(getPort()));

        run();
    }

    private void run() {
        while (true) {
            try {
                setClientSocket(getServerSocket().accept());
                Thread newUserThread = new Thread(new Task(getApp(), getClientSocket()));
                newUserThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

}