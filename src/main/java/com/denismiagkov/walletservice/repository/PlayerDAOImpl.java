package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.domain.model.Player;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.sql.*;
import java.util.*;

public class PlayerDAOImpl implements PlayerDAO {

    DatabaseConnection dbConnection;

    public PlayerDAOImpl() {
        try {
            this.dbConnection = new DatabaseConnection();
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Player savePlayer(Player player) {
        String insertPlayer = "INSERT INTO wallet.players (name, surname, email) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertPlayer)) {
            prStatement.setString(1, player.getFirstName());
            prStatement.setString(2, player.getLastName());
            prStatement.setString(3, player.getEmail());
            prStatement.executeUpdate();
            player.setId(getPlayerId(player));
            System.out.println(player.getId());
            return player;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(1);
        }
        return null;
    }

    @Override
    public void saveEntry(Entry entry) {
        String insertEntry = "INSERT INTO wallet.entries (login, password, player_id) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertEntry)) {
            prStatement.setString(1, entry.getLogin());
            prStatement.setString(2, entry.getPassword());
            prStatement.setInt(3, entry.getPlayerId());
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(2);
        }
    }

    @Override
    public Set<Player> getAllPlayers() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM wallet.players");
            Set<Player> allPlayers = new HashSet<>();
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                allPlayers.add(new Player(name, surname, email));
            }
            return allPlayers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        @Override
    public Map<String, String> getAllEntries() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM wallet.entries");
            Map<String, String> allEntries = new HashMap<>();
            while (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                allEntries.put(login, password);
            }
            return allEntries;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPlayerId(Player player) {
        String getPlayerId = "SELECT id FROM wallet.players WHERE name = ? AND surname = ? and email = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(getPlayerId)) {
            prStatement.setString(1, player.getFirstName());
            prStatement.setString(2, player.getLastName());
            prStatement.setString(3, player.getEmail());
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt("id");
                return playerId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + e.getLocalizedMessage());
            System.out.println(3);
        }
        return -1;
    }

    public int getPlayerId(String login, String password) {
        String queryPlayerId = "SELECT player_id FROM wallet.entries WHERE login = ? AND password = ?";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement(queryPlayerId);
            prStatement.setString(1, login);
            prStatement.setString(2, password);
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt("player_id");
                return playerId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + e.getLocalizedMessage());
            System.out.println(3);
        }
        return -1;
    }

//    public int setEntryId(Entry entry) {
//        String getEntryId = "SELECT id FROM wallet.entries WHERE login = ? AND password = ?";
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement prStatement = connection.prepareStatement(getEntryId)) {
//            prStatement.setString(1, entry.getLogin());
//            prStatement.setString(2, entry.getPassword());
//            ResultSet rs = prStatement.executeQuery();
//            while (rs.next()) {
//                int entryId = rs.getInt("id");
//                return entryId;
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return -1;
//    }
}
