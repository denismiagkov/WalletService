package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Player;
import org.apache.commons.configuration2.ex.ConfigurationException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AccountDAOImpl implements AccountDAO {

    DatabaseConnection dbConnection;

    public AccountDAOImpl() {
        try {
            this.dbConnection = new DatabaseConnection();
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveAccount(Player player) {
        String insertAccount = "INSERT INTO wallet.accounts (number, balance, player_id) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertAccount)) {
            prStatement.setString(1, player.getAccount().getNumber());
            prStatement.setBigDecimal(2, player.getAccount().getBalance());
            prStatement.setInt(3, player.getId());
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
