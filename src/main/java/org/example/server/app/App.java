package org.example.server.app;

import org.example.server.app.controllers.CityController;
import org.example.server.app.services.CityService;
import org.example.server.http.ContentType;
import org.example.server.http.HttpStatus;
import lombok.AccessLevel;
import lombok.Setter;
import org.example.server.server.Request;
import org.example.server.server.Response;
import org.example.server.server.ServerApp;


public class App implements ServerApp {
    @Setter(AccessLevel.PRIVATE)
    private CityController cityController;

    public App() {
        setCityController(new CityController(new CityService()));
    }

    public Response handleRequest(Request request) {


        switch (request.getMethod()) {
            case GET: {
                if (request.getPathname().equals("/cities")) {
                    return this.cityController.getCities();
                }
            }
        }

        return new Response(HttpStatus.NOT_FOUND, ContentType.JSON, "{ \"error\": \"Not Found\", \"data\": null }");
    }
}
