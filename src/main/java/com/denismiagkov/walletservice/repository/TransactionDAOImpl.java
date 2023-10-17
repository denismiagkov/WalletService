package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.math.BigDecimal;
import java.sql.*;

public class TransactionDAOImpl implements TransactionDAO {

    DatabaseConnection dbConnection;

    public TransactionDAOImpl() {
        try {
            this.dbConnection = new DatabaseConnection();
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void saveTransaction(Transaction transaction) {
        String insertTransaction = "INSERT INTO wallet.transactions (commit_time, item_type, amount, account) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertTransaction)) {
            prStatement.setTimestamp(1, transaction.getTime());
            prStatement.setString(2, transaction.getType().toString());
            prStatement.setBigDecimal(3, transaction.getAmount());
            prStatement.setInt(4, transaction.getAccountId());
            prStatement.executeUpdate();
            transaction.setId(getTransactionId(transaction));
            System.out.println(transaction.getId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(1);
        }
    }

    public int getTransactionId(Transaction transaction) {
        String queryTransactionId = "SELECT id FROM wallet.transactions WHERE commit_time = ? AND item_type = ? " +
                "AND amount = ? AND account = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryTransactionId)) {
            prStatement.setTimestamp(1, transaction.getTime());
            prStatement.setString(2, transaction.getType().toString());
            prStatement.setBigDecimal(3, transaction.getAmount());
            prStatement.setInt(4, transaction.getAccountId());
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                return transactionId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

}

