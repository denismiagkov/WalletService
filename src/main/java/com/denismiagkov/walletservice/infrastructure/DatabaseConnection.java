package com.denismiagkov.walletservice.infrastructure;

import com.denismiagkov.walletservice.PropertyFile;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public DatabaseConnection() {
        this.URL = PropertyFile.getProperties("URL");
        this.USERNAME = PropertyFile.getProperties("USER_NAME");
        this.PASSWORD = PropertyFile.getProperties("USER_PASSWORD");
    }

    public DatabaseConnection(String url, String username, String password) {
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("entered into connection");
        return DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
    }
}

