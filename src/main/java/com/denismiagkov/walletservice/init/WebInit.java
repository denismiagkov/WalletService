package com.denismiagkov.walletservice.init;

import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import org.springframework.beans.factory.annotation.Autowired;

public class WebInit {

    LiquibaseApp liquibaseApp;

    @Autowired
    public WebInit(LiquibaseApp liquibaseApp) {

        this.liquibaseApp = liquibaseApp;
    }

    public void start() {
        this.liquibaseApp.start();
    }
}
