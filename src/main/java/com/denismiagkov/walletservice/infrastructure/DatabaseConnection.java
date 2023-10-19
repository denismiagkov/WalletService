package com.denismiagkov.walletservice.infrastructure;

import com.denismiagkov.walletservice.PropertyFile;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    PropertyFile propertyFile;
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public DatabaseConnection()  {
        this.propertyFile = new PropertyFile();
        this.URL = propertyFile.getProperties("URL");
        this.USERNAME = propertyFile.getProperties("USER_NAME");
        this.PASSWORD = propertyFile.getProperties("USER_PASSWORD");
    }

    public DatabaseConnection(File file)  {
        this.propertyFile = new PropertyFile(file);
        this.URL = propertyFile.getProperties("URL");
        this.USERNAME = propertyFile.getProperties("USER_NAME");
        this.PASSWORD = propertyFile.getProperties("USER_PASSWORD");
    }

    public DatabaseConnection(String url, String username, String password)  {
        this.propertyFile = null;
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    public String getURL() {
        return URL;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
    }
}

//public class DatabaseConnection {
//    PropertyFile propertyFile;
//
//    public DatabaseConnection()  {
//        this.propertyFile = new PropertyFile();
//    }
//    public DatabaseConnection(File file)  {
//        this.propertyFile = new PropertyFile(file);
//    }
//
//
//    public Connection getConnection() throws SQLException {
//        String url = propertyFile.getProperties("URL");
//        String user_name = propertyFile.getProperties("USER_NAME");
//        String user_password = propertyFile.getProperties("USER_PASSWORD");
//        return DriverManager.getConnection(url, user_name, user_password);
//    }
//}
