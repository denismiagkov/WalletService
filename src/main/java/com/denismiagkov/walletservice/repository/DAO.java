package com.denismiagkov.walletservice.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    String URL = "jdbc:postgresql://localhost:5432/wallet_service";
    String USER_NAME = "wallet_service";
    String USER_PASSWORD = "123";

    PlayerDAOImpl playerDAOImpl;

    public DAO() {
        this.playerDAOImpl = new PlayerDAOImpl();
    }

    public void saveData(String firstName, String lastName, String email){
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)){
            playerDAOImpl.savePlayer(connection, firstName, lastName, email);
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }

    }
}
