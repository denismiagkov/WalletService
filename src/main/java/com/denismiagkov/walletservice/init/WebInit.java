package com.denismiagkov.walletservice.init;

import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;

public class WebInit {

    public static  void start() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
        liquibaseApp.start();
    }
}
