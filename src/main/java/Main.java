import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.init.WebInit;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.sql.*;

/**
 * Это класс Main - вход в программу
 */
public class Main {
    /**
     * Это метод  main() - точка входа в программу.
     */
    public static void main(String[] args) throws SQLException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan("com.denismiagkov.walletservice");
        WebInit.start();
    }
}