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
    @JsonAlias({"Username"})
    @Setter(AccessLevel.PRIVATE)
    String username;
    @JsonAlias({"Password"})
    @Setter(AccessLevel.PRIVATE)
    String password;

    // Jackson needs the default constructor
    public UserCredentials() {}
}
