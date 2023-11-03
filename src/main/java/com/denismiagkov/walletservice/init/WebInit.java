package com.denismiagkov.walletservice.init;

import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("webInitBean")
public class WebInit {

    public LiquibaseApp liquibaseApp;

    @Autowired
    public WebInit(LiquibaseApp liquibaseApp) {

        this.liquibaseApp = liquibaseApp;
        System.out.println("WebInit created");
    }

    public void start() {

        liquibaseApp.start();
    }
}
