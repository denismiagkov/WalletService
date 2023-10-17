package com.denismiagkov.walletservice.repository;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    String URL = "jdbc:postgresql://localhost:5431/wallet_service";
    String USER_NAME = "wallet_service";
    String USER_PASSWORD = "123";

//    Configurations configs = new Configurations();
//    Configuration config = configs.properties("config.properties");
//
//    String URL = config.getString("URL");
//    String USER_NAME = config.getString("USER_NAME");
//    String USER_PASSWORD = config.getString("USER_PASSWORD");

    public DatabaseConnection() throws ConfigurationException {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD);
    }
}
