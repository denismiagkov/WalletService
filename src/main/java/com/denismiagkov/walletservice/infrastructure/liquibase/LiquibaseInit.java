package com.denismiagkov.walletservice.infrastructure.liquibase;

import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Утилитный класс для создания служебной схемы Liquibase
 */
@Component
public class LiquibaseInit {

    /**
     * Подключение к базе данных
     */
    DatabaseConnection dbConnection;
    private final String QUERY_CREATE_MIGRATION_SCHEMA = "CREATE SCHEMA IF NOT EXISTS migration";

    /**
     * Конструктор класса
     */
    @Autowired
    public LiquibaseInit(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Метод создает схему базы данных, в которой будут созданы служебные таблицы Liquibase
     */
    @PostConstruct
    public void start() {
        try (Connection connection = this.dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_CREATE_MIGRATION_SCHEMA)) {
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
