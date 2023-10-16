package com.denismiagkov.walletservice.repository;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.junit.jupiter.api.Test;

import javax.naming.ConfigurationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    public void test() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = null;
        try {
            config = configs.properties("config.properties");
        } catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
            System.out.println(e.getMessage());
        }

        String url = config.getString("database.url");
        String username = config.getString("database.username");
        String password = config.getString("database.password");

        // Используем параметры для создания подключения к базе данных
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}