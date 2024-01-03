package org.example.backend.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao implements DAO<User> {
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    Connection connection;

    @Setter(AccessLevel.PRIVATE)
    ArrayList<User> userCache;

    public UserDao(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(User user) {
        String insertStmt =
                "INSERT INTO users (name, elopoints, coins, password, bio, image) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStmt);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, 100);
            preparedStatement.setInt(3, 20);
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, "hello World");
            preparedStatement.setString(6, ":)");
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User read(int id) {
        User user = null;

        String readStmt =
            "SELECT user_id, name, elopoints, coins, password, bio, image " +
            "FROM users " +
            "WHERE user_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            preparedStatement.setInt(1, id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    user = new User(
                            result.getInt("user_id"),
                            result.getString("name"),
                            result.getInt("elopoints"),
                            result.getInt("coins"),
                            result.getString("password"),
                            result.getString("bio"),
                            result.getString("image")
                    );
                }
                setUserCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User read(String name) {
        User user = null;

        String readStmt =
                "SELECT user_id, name, elopoints, coins, password, bio, image " +
                        "FROM users " +
                        "WHERE name = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            preparedStatement.setString(1, name);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    user = new User(
                            result.getInt("user_id"),
                            result.getString("name"),
                            result.getInt("elopoints"),
                            result.getInt("coins"),
                            result.getString("password"),
                            result.getString("bio"),
                            result.getString("image")
                    );
                }
                setUserCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User read(String name, String password) {
        User user = null;

        String readStmt =
                "SELECT user_id, name, elopoints, coins, password, bio, image " +
                        "FROM users " +
                        "WHERE name = ? AND password = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    user = new User(
                            result.getInt("user_id"),
                            result.getString("name"),
                            result.getInt("elopoints"),
                            result.getInt("coins"),
                            result.getString("password"),
                            result.getString("bio"),
                            result.getString("image")
                    );
                }
                setUserCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void update(User user) {
        String updateStmt =
            "UPDATE users " +
            "SET name = ?, elopoints = ?, coins = ?, password = ?, bio = ?, image = ? " +
            "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStmt);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getElopoints());
            preparedStatement.setInt(3, user.getCoins());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getBio());
            preparedStatement.setString(6, user.getImage());
            preparedStatement.setInt(7, user.getUser_id());
            preparedStatement.executeUpdate();
            setUserCache(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int user_id, String newUsername, String newBio, String newImage) {
        String updateStmt =
                "UPDATE users " +
                        "SET name = ?, bio = ?, image = ? " +
                        "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStmt);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, newBio);
            preparedStatement.setString(3, newImage);
            preparedStatement.setInt(4, user_id);
            preparedStatement.executeUpdate();
            setUserCache(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String deleteStmt = "DELETE FROM users WHERE user_id = ?;";
        String deleteCardsStmt = "DELETE FROM cards WHERE user_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStmt);
            PreparedStatement secondPreparedStatement = connection.prepareStatement(deleteCardsStmt);
            // deleting from Users table
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            // deleting from Cards table
            secondPreparedStatement.setInt(1, id);
            secondPreparedStatement.executeUpdate();
            setUserCache(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> readAll() {
        ArrayList<User> users = new ArrayList();

        String readStmt =
                "SELECT user_id, name, elopoints, coins, password, bio, image " +
                "FROM users;";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(readStmt);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                User newUser = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)
                );
                users.add(newUser);
            }
            setUserCache(users);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
