package com.denismiagkov.walletservice.init;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.in.console.Console;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;

public class ConsoleInit {
    public static void start(){
        DatabaseConnection dbConnection = new DatabaseConnection();
        LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
        liquibaseApp.start();

        Service service = new Service();
        Controller controller = new Controller(service);
        Console console = new Console(controller);
        console.start();
    }

}
