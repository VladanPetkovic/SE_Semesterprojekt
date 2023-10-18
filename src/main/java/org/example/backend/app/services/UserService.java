package org.example.backend.app.services;

import org.example.backend.app.models.User;
import lombok.AccessLevel;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    @Setter(AccessLevel.PRIVATE)
    private List<User> userData;

    public UserService() {
        setUserData(new ArrayList<>());
        userData.add(new User(0, "Vladan", 100, 20));
        userData.add(new User(0, "Alice", 100, 20));
        userData.add(new User(0, "John", 100, 20));
    }

    public User getUserById(int id) {
        String stat = "";

        User foundUser = userData.stream()
                .filter(user -> id == user.getUser_id())
                .findAny()
                .orElse(null);

        return foundUser;
    }

    public List<User> getUsers() {
        return userData;
    }

    public void addUser(User user) {

        String stmt = "INSERT INTO users (name, elopoints, coins) VALUES (?,?,?);";
        DatabaseService dbService = new DatabaseService(); // move to constructor
        Connection connection = dbService.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getElopoints());
            preparedStatement.setInt(3, user.getCoins());
            preparedStatement.executeQuery();
        } catch(Exception e) {
            e.printStackTrace();
        }

        userData.add(user);
    }

    public void removeUser(int id) {
        userData.removeIf(user -> id == user.getUser_id());
    }
}
