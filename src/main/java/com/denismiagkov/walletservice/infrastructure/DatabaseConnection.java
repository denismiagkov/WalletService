package com.denismiagkov.walletservice.infrastructure;

import com.denismiagkov.walletservice.configuration.ConnectionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс подключения к базе данных
 */
@Component
public class DatabaseConnection {
    /**
     * Конфигурационные данные для подключения к базе данных:
     */
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;
    private final String DRIVER;


    /**
     * Базовый конструктор класса (значения для подключения принимаются из конфигурационного файла
     * через посредство класса {@link ConnectionConfig}
     */
    @Autowired
    public DatabaseConnection(ConnectionConfig connectionConfig) {
        this.URL = connectionConfig.getUrl();
        this.USERNAME = connectionConfig.getUsername();
        this.PASSWORD = connectionConfig.getPassword();
        this.DRIVER = connectionConfig.getDriver();
    }

    /**
     * Конструктор класса (значения для подключения задаются в параметрах конструктора) применятся
     * для выполнения тестирования
     */
    public DatabaseConnection(String url, String username, String password, String driver) {
        this.URL = url;
        this.USERNAME = username;
        this.PASSWORD = password;
        this.DRIVER = driver;
    }

    /**
     * Метод создает соединение с базой данных
     *
     * @return Connection соединение с базой данных
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(this.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
    }
}

