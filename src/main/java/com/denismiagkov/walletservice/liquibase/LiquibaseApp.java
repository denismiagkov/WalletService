package com.denismiagkov.walletservice.liquibase;

import com.denismiagkov.walletservice.PropertyFile;
import com.denismiagkov.walletservice.repository.DatabaseConnection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LiquibaseApp {

    PropertyFile propertyFile;

    public LiquibaseApp() {
        this.propertyFile = new PropertyFile();
    }

    public Liquibase initLiquibase() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String queryCreateMigrationSchema = "CREATE SCHEMA IF NOT EXISTS migration";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryCreateMigrationSchema)) {
            statement.executeUpdate();

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    new JdbcConnection(connection));
            database.setLiquibaseSchemaName("migration");
            String changelogFile = propertyFile.getProperties("src/main/resources/db/changelog/changelog.xml");
            Liquibase liquibase = new Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);
            return liquibase;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
