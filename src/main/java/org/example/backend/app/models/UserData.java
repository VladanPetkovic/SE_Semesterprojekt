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
    @JsonAlias({"name"})
    @Setter(AccessLevel.PRIVATE)
    String name;
    @JsonAlias({"bio"})
    @Setter(AccessLevel.PRIVATE)
    String bio;
    @JsonAlias({"image"})
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
