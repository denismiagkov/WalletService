import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.liquibase.LiquibaseApp;

import java.sql.*;

/**
 * Это точка входа в программу
 */
public class Main {
    /**
     * Это класс  main()
     */
    public static void main(String[] args) throws SQLException {

        DatabaseConnection dbConnection = new DatabaseConnection();
        LiquibaseApp liquibase = new LiquibaseApp(dbConnection);
        liquibase.start();

        Service service = new Service();
        Controller controller = new Controller(service);
        Console console = new Console(controller);
        console.start();
    }
}
