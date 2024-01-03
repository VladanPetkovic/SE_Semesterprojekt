package org.example.backend.server;

import org.example.backend.http.ContentType;
import org.example.backend.http.HttpStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;


@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class Response {
    private int statusCode;
    private String statusMessage;
    private String contentType;
    private String content;
    private String authorization;

    public Response(HttpStatus httpStatus, ContentType contentType, String content) {
        setStatusCode(httpStatus.getCode());
        setContentType(contentType.getType());
        setStatusMessage(httpStatus.getMessage());
        setContent(content);
    }

    public Response(HttpStatus httpStatus, ContentType contentType, String authorization, String content) {
        setStatusCode(httpStatus.getCode());
        setContentType(contentType.getType());
        setStatusMessage(httpStatus.getMessage());
        setContent(content);
        setAuthorization(authorization);
    }

    protected String build() {
        return "HTTP/1.1 " + getStatusCode() + " " + getStatusMessage() + "\r\n" +
                "Content-Type: " + getContentType() + "\r\n" +
                "Content-Length: " + getContent().length() + "\r\n" +
                "Authorization: " + getAuthorization() + "\r\n" +
                "\r\n" +
                getContent();
    }
}