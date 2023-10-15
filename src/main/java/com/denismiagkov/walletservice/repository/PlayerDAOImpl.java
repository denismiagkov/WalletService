package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Player;

import java.sql.*;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {

    @Override
    public void savePlayer(Connection connection, Player player) {
        String insertPlayer = "INSERT INTO wallet.players (name, surname, email) VALUES (?, ?, ?)";
        try (PreparedStatement prStatement = connection.prepareStatement(insertPlayer)) {
            prStatement.setString(1, player.getFirstName());
            prStatement.setString(2, player.getLastName());
            prStatement.setString(3, player.getEmail());
            prStatement.executeUpdate();
            player.setId(setPlayerId(connection, player));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int setPlayerId(Connection connection, Player player) {
        String getPlayerId = "SELECT id FROM wallet.players WHERE name = ? AND surname = ? and email = ?";
        try (PreparedStatement prStatement = connection.prepareStatement(getPlayerId)) {
            prStatement.setString(1, player.getFirstName());
            prStatement.setString(2, player.getLastName());
            prStatement.setString(3, player.getEmail());
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt("id");
                return playerId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
