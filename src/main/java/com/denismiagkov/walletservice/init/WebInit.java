package com.denismiagkov.walletservice.init;

import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import org.springframework.beans.factory.annotation.Autowired;

public class WebInit {

    public static LiquibaseApp liquibaseApp;

    @Autowired
    public WebInit(LiquibaseApp liquibaseApp) {

        this.liquibaseApp = liquibaseApp;
    }

    public static void start() {

        liquibaseApp.start();
    }
}
