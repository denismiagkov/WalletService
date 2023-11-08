package com.denismiagkov.walletservice.infrastructure.liquibase;

import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import jakarta.annotation.PostConstruct;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Класс-"обертка" для реализации функционала Liqubase
 * */
@Component
public class LiquibaseApp {

    /**
     * Подключение к базе данных
     * */
    DatabaseConnection dbConnection;

    /**
     * Конструктор класса
     * */
    @Autowired
    public LiquibaseApp(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Метод создает схему базы данных, в которой будут созданы служебные таблицы Liquibase, инициализирует
     * объект Liquibase и запускает скрипты миграции данных
     * */
    @PostConstruct
    public Liquibase start() {
        String queryCreateMigrationSchema = "CREATE SCHEMA IF NOT EXISTS migration";

        try (Connection connection = this.dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryCreateMigrationSchema)) {
            statement.executeUpdate();

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    new JdbcConnection(connection));
            database.setLiquibaseSchemaName("migration");
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграции успешно выполнены!");
            return liquibase;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
