import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;

import java.sql.*;

/**
 * Это класс Main - вход в программу
 */
public class Main {
    /**
     * Это метод  main() - точка входа в программу.
     */
    public static void main(String[] args) throws SQLException {

        DatabaseConnection dbConnection = new DatabaseConnection();
        LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
        liquibaseApp.start();

        Service service = new Service();
        Controller controller = new Controller(service);
        Console console = new Console(controller);
        console.start();
    }
}