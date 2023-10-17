package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import org.apache.commons.configuration2.ex.ConfigurationException;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    public BigDecimal getCurrentBalance(int playerId) {
        String queryCurrentBalance = "SELECT balance FROM wallet.accounts WHERE player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryCurrentBalance)) {
            prStatement.setInt(1, playerId);
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                BigDecimal balance = rs.getBigDecimal("balance");
                return balance;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new BigDecimal(-1);
    }



    public List<String> getTransactionHistory(int playerId) {
        String queryTransactionHistory = "SELECT commit_time, item_type, amount FROM wallet.transactions " +
                "WHERE player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryTransactionHistory)) {
            prStatement.setInt(1, playerId);
            List<String> transactionHistory = new ArrayList<>();
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                String transaction = rs.getTimestamp("commit_time") +
                        ": " + rs.getString("item_type") +
                        ": " + rs.getFloat("amount");
                transactionHistory.add(transaction);
            }
            return transactionHistory;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void increaseBalance(int playerId, BigDecimal amount) {
        BigDecimal balance = getCurrentBalance(playerId).add(amount);
        changeBalance(playerId, balance);
    }

    public void decreaseBalance(int playerId, BigDecimal amount) {
        BigDecimal balance = getCurrentBalance(playerId).subtract(amount);
        changeBalance(playerId, balance);
    }

    public void changeBalance(int playerId, BigDecimal balance) {
        String queryIncreaseBalance = "UPDATE wallet.accounts SET balance = ? WHERE player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryIncreaseBalance)) {
            prStatement.setBigDecimal(1, balance);
            prStatement.setInt(2, playerId);
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //     * @throws NotEnoughFundsOnAccountException в случае, если на счете игрока недостаточно денежных средств для
//     *                                          совершения транзакции
    public boolean areFundsEnough(int playerId, BigDecimal amount) {
        if (getCurrentBalance(playerId).compareTo(amount) < 0) {
            throw new NotEnoughFundsOnAccountException();
        } else {
            return true;
        }
    }


}
