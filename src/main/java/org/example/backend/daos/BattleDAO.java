package org.example.backend.daos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.app.models.Battle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BattleDAO implements DAO<Battle> {
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    Connection connection;

    @Setter(AccessLevel.PRIVATE)
    ArrayList<Battle> battleCache;

    public BattleDAO(Connection connection) { setConnection(connection);}

    @Override
    public void create(Battle battle) {
        String insertStmt =
                "INSERT INTO battles (user_winner_id, user_looser_id, log, tie) " +
                "VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertStmt);
            preparedStatement.setInt(1, battle.getUser_winner_id());
            preparedStatement.setInt(2, battle.getUser_looser_id());
            preparedStatement.setString(3, battle.getLog());
            preparedStatement.setBoolean(4, battle.getTie());
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Battle read(int id) {
        Battle battle = null;

        String readStmt =
                "SELECT * " +
                "FROM battles " +
                "WHERE battle_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            preparedStatement.setInt(1, id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    battle = new Battle(
                            result.getInt("battle_id"),
                            result.getInt("user_winner_id"),
                            result.getInt("user_looser_id"),
                            result.getString("log"),
                            result.getBoolean("tie")
                    );
                }
                setBattleCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return battle;
    }

    public Battle readLast() {
        Battle battle = null;

        String readStmt =
                "SELECT * " +
                "FROM battles " +
                "ORDER BY battle_id DESC " +
                "LIMIT 1;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    battle = new Battle(
                            result.getInt("battle_id"),
                            result.getInt("user_winner_id"),
                            result.getInt("user_looser_id"),
                            result.getString("log"),
                            result.getBoolean("tie")
                    );
                }
                setBattleCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return battle;
    }

    public int readWinnerCount(int user_id) {
        int win_count = 0;
        String readStmt =
                "SELECT COUNT(*) " +
                "AS win_count " +
                "FROM battles " +
                "WHERE user_winner_id = ? AND tie = FALSE;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            preparedStatement.setInt(1, user_id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    win_count = result.getInt("win_count");
                }
                setBattleCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return win_count;
    }

    public int readLooserCount(int user_id) {
        int loss_count = 0;
        String readStmt =
                "SELECT COUNT(*) " +
                "AS loss_count " +
                "FROM battles " +
                "WHERE user_looser_id = ? AND tie = FALSE;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(readStmt);
            preparedStatement.setInt(1, user_id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    loss_count = result.getInt("loss_count");
                }
                setBattleCache(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loss_count;
    }

    @Override
    public void update(Battle battle) {
        String updateStmt =
                "UPDATE battles " +
                "SET user_winner_id = ?, user_looser_id = ?, log = ?, tie = ? " +
                "WHERE battle_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateStmt);
            preparedStatement.setInt(1, battle.getUser_winner_id());
            preparedStatement.setInt(2, battle.getUser_looser_id());
            preparedStatement.setString(3, battle.getLog());
            preparedStatement.setBoolean(4, battle.getTie());
            preparedStatement.setInt(5, battle.getBattle_id());
            preparedStatement.executeUpdate();
            setBattleCache(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String deleteStmt = "DELETE FROM battles WHERE battle_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteStmt);
            // deleting from Battle table
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            setBattleCache(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Battle> readAll() {
        ArrayList<Battle> battles = new ArrayList<Battle>();

        String readStmt =
                "SELECT * " +
                "FROM battles;";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(readStmt);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Battle newBattle = new Battle(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getBoolean(5)
                );
                battles.add(newBattle);
            }
            setBattleCache(battles);
            return battles;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
