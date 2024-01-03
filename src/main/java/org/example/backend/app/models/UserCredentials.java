package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCredentials {
    @JsonAlias({"username"})
    @Setter(AccessLevel.PRIVATE)
    String username;
    @JsonAlias({"password"})
    @Setter(AccessLevel.PRIVATE)
    String password;

    // Jackson needs the default constructor
    public UserCredentials() {}
}
