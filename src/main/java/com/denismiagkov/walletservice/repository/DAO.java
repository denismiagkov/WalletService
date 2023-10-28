//package com.denismiagkov.walletservice.repository;
//
//import com.denismiagkov.walletservice.domain.model.Account;
//import com.denismiagkov.walletservice.domain.model.Player;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DAO {
//    String URL = "jdbc:postgresql://localhost:5432/wallet_service";
//    String USER_NAME = "wallet_service";
//    String USER_PASSWORD = "123";
//
//    PlayerDAOImpl playerDAOImpl;
//    AccountDAOImpl accountDAOImpl;
//
//    public DAO() {
//       // this.playerDAOImpl = new PlayerDAOImpl();
//        this.accountDAOImpl = new AccountDAOImpl();
//    }
//
//    public void savePlayer(Player player) {
//        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {
//            playerDAOImpl.savePlayer(connection, player);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void saveAccount(Player player) {
//        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD)) {
//            accountDAOImpl.saveAccount(connection, player);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
