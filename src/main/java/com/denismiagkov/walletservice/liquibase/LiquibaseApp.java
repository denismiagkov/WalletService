package com.denismiagkov.walletservice.liquibase;

import com.denismiagkov.walletservice.PropertyFile;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LiquibaseApp {

    PropertyFile propertyFile;

    public LiquibaseApp() {
        this.propertyFile = new PropertyFile();
    }

    public Liquibase initLiquibase() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        String queryCreateMigrationSchema = "CREATE SCHEMA IF NOT EXISTS ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryCreateMigrationSchema)) {
            String schemaName = propertyFile.getProperties("LIQUIBASE_SCHEMA_NAME");
            statement.setString(1, "migration");
            statement.executeUpdate();

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    new JdbcConnection(connection));
            database.setLiquibaseSchemaName(schemaName);
            String changelogFile = propertyFile.getProperties("LIQUIBASE_CHANGELOG_FILE");
            Liquibase liquibase = new Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);
            return liquibase;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
