package org.example.backend.http;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "CREATED"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED_ERROR(401, "Unauthorized Error"),
    FORBIDDEN_ERROR(403, "Forbidden Error"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Data conflict"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private int code;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private String message;

    HttpStatus(int code, String message) {
        setCode(code);
        setMessage(message);
    }
}
