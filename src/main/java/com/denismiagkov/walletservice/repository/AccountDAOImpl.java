package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public void saveAccount(Connection connection, Player player ){
        String insertPlayer = "INSERT INTO wallet.accounts (number, balance, player_id) VALUES (?, ?, ?)";
        try (PreparedStatement prStatement = connection.prepareStatement(insertPlayer)) {
            prStatement.setString(1, player.getAccount().getNumber());
            prStatement.setBigDecimal(2, player.getAccount().getBalance());
            prStatement.setInt(3, player.getId());
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
