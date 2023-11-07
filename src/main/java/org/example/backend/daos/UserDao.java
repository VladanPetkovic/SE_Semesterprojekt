package org.example.backend.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao implements DAO<User> {
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
            Connection connection;

    UserDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(User user) {
        String insertStmt = "INSERT INTO users (name, elopoints, coins) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertStmt);
            preparedStatement.setString(1, user.getName());
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User read(int id) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public ArrayList<User> readAll() {
        return new ArrayList<>();
    }
}
