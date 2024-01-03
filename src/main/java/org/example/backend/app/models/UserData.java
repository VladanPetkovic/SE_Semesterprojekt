package org.example.backend.app.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserData {
    @JsonAlias({"Name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"Bio"})
    @Setter(AccessLevel.PRIVATE)
    String bio;
    @JsonAlias({"Image"})
    @Setter(AccessLevel.PRIVATE)
    String image;

    // Jackson needs the default constructor
    public UserData() {}

    public UserData(User other) {
        setName(other.getName());
        setBio(other.getBio());
        setImage(other.getImage());
    }
}
