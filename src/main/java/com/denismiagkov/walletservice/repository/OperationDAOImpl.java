package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.Player;

import java.sql.*;
import java.util.*;

public class OperationDAOImpl implements OperationDAO {

    DatabaseConnection dbConnection;

    public OperationDAOImpl() {
        this.dbConnection = new DatabaseConnection();
    }


    @Override
    public void saveOperation(Operation operation) {
        String insertOperation = "INSERT INTO wallet.operations (operation_type, perform_time, operation_status," +
                "player_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertOperation)) {
            prStatement.setString(1, operation.getType().toString());
            prStatement.setTimestamp(2, operation.getTime());
            prStatement.setString(3, operation.getStatus().toString());
            prStatement.setInt(4, operation.getPlayerId());
            prStatement.executeUpdate();
            operation.setId(getOperationId(operation));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(1);
        }
    }

    @Override
    public List<String> getLog() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM wallet.operations");
            List<String> log = new ArrayList<>();
            while (rs.next()) {
                String type = rs.getString("operation_type");
                String time = rs.getString("perform_time");
                String status = rs.getString("operation_status");
                String playerId = rs.getString("player_id");
                String operation = "{" + type + " - " + time + " - " + status + " - " + playerId + "}";
                log.add(operation);
            }
            return log;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOperationId(Operation operation) {
        String getOperationId = "SELECT id FROM wallet.operations WHERE operation_type = ? AND perform_time = ? " +
                "AND operation_status = ? AND player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(getOperationId)) {
            prStatement.setString(1, operation.getType().toString());
            prStatement.setTimestamp(2, operation.getTime());
            prStatement.setString(3, operation.getStatus().toString());
            prStatement.setInt(4, operation.getPlayerId());
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int operationId = rs.getInt("id");
                return operationId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(3);
        }
        return -1;
    }

}
