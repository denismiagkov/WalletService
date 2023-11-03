package com.denismiagkov.walletservice.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;
    private final String DRIVER;

    @Autowired
    public DatabaseConnection(ConnectionConfig connectionConfig) {
        this.URL = connectionConfig.getUrl();
        this.USERNAME = connectionConfig.getUsername();
        this.PASSWORD = connectionConfig.getPassword();
        this.DRIVER = connectionConfig.getDriver();
    }

    public DatabaseConnection(String url, String username, String password, String driver) {
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.DRIVER = driver;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(this.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
    }
}

