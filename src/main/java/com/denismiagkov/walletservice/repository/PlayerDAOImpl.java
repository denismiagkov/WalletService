package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {

    @Override
    public void savePlayer(Connection connection, String firstName, String lastName, String email) {
        String insertPlayer = "INSERT INTO wallet.players (name, surname, email) VALUES (?, ?, ?)";
        try (PreparedStatement prStatement = connection.prepareStatement(insertPlayer)) {
            prStatement.setString(1, firstName);
            prStatement.setString(2, lastName);
            prStatement.setString(3, email);
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public List<Player> getAllPlayers() {
        return null;
    }


    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public void deletePlayer() {

    }
}
