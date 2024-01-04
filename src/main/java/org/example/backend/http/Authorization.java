package org.example.backend.http;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Authorization {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private String type;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private String token;

    public Authorization() {
        setType("Bearer");
    }

    public String getAuth(String name) {
        return getType() + " " + name + "-mtcgToken";
    }

    public String getUsernameFromToken(String token) {
        return token.substring(7, token.length() - 10);
    }
}
