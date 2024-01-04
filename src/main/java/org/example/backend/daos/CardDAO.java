package org.example.backend.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Card;
import org.example.backend.app.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardDAO implements DAO<Card> {
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    Connection connection;

    @Setter(AccessLevel.PRIVATE)
    ArrayList<Card> cardCache;

    public CardDAO(Connection connection) {
        setConnection(connection);
    }

    @Override
    public void create(Card card) {
        String insertStmt =
                "INSERT INTO cards (card_id, user_id, name, damage, element_type, isindeck) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStmt);
            preparedStatement.setString(1, card.getCard_id());
            preparedStatement.setInt(2, card.getUser_id());
            preparedStatement.setString(3, card.getName());
            preparedStatement.setFloat(4, card.getDamage());
            preparedStatement.setInt(5, card.getElement_type());
            preparedStatement.setBoolean(6, card.getIsInDeck());
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Card read(int id) {
        // does not work --> purpose
        return null;
    }

    public Card read(String card_id) {
        Card card = null;

        String readStmt =
                "SELECT card_id, user_id, name, damage, element_type, isindeck " +
                "FROM cards " +
                "WHERE card_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            preparedStatement.setString(1, card_id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    card = new Card(
                            result.getString("card_id"),
                            result.getInt("user_id"),
                            result.getString("name"),
                            result.getFloat("damage"),
                            result.getInt("element_type"),
                            result.getBoolean("isindeck")
                    );
                }
                setCardCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    @Override
    public void update(Card card) {
        String updateStmt =
                "UPDATE users " +
                "SET user_id = ?, name = ?, damage = ?, element_type = ?, isindeck = ? " +
                "WHERE card_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStmt);
            preparedStatement.setInt(1, card.getUser_id());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setFloat(3, card.getDamage());
            preparedStatement.setInt(4, card.getElement_type());
            preparedStatement.setBoolean(5, card.getIsInDeck());
            preparedStatement.setString(6, card.getCard_id());
            preparedStatement.executeUpdate();
            setCardCache(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        // does not work --> purpose
    }

    public void delete(String card_id) {
        String deleteStmt = "DELETE FROM cards WHERE card_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStmt);
            // deleting from Cards table
            preparedStatement.setString(1, card_id);
            preparedStatement.executeUpdate();
            setCardCache(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Card> readAll() {
        ArrayList<Card> cards = new ArrayList<Card>();

        String readStmt =
                "SELECT * " +
                "FROM cards;";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(readStmt);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Card newCard = new Card(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getFloat(4),
                        resultSet.getInt(5),
                        resultSet.getBoolean(6)
                );
                cards.add(newCard);
            }
            setCardCache(cards);
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Card> readAll(int user_id) {
        ArrayList<Card> cards = new ArrayList<Card>();

        String readStmt =
                "SELECT * " +
                "FROM cards " +
                "WHERE user_id = ?;";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(readStmt);
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Card newCard = new Card(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getFloat(4),
                        resultSet.getInt(5),
                        resultSet.getBoolean(6)
                );
                cards.add(newCard);
            }
            setCardCache(cards);
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
