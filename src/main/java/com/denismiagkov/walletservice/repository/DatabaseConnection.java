package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.PropertyFile;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    PropertyFile propertyFile;

    public DatabaseConnection()  {
        this.propertyFile = new PropertyFile();
    }

    public Connection getConnection() throws SQLException {
        String url = propertyFile.getProperties("URL");
        String user_name = propertyFile.getProperties("USER_NAME");
        String user_password = propertyFile.getProperties("USER_PASSWORD");
        return DriverManager.getConnection(url, user_name, user_password);
    }
}
