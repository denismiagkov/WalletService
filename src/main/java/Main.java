import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.liquibase.LiquibaseApp;
import com.denismiagkov.walletservice.repository.DatabaseConnection;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.sql.*;
import java.util.*;

/**
 * Это точка входа в программу
 */
public class Main {
    /**
     * Это класс  main()
     */
    public static void main(String[] args) throws SQLException, ConfigurationException {
//
        DatabaseConnection dbConnection = new DatabaseConnection();
        String queryCreateMigrationSchema = "CREATE SCHEMA IF NOT EXISTS migration;";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryCreateMigrationSchema)) {
            statement.executeUpdate();

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                    new JdbcConnection(connection));
            database.setLiquibaseSchemaName("migration");
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
                    new ClassLoaderResourceAccessor(), database);
              liquibase.update();
            System.out.println("Миграции успешно выполнены!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Service service = new Service();
        Controller controller = new Controller(service);
        Console console = new Console(controller);
        console.start();


//        DatabaseConnection dbConnection = new DatabaseConnection();
//        try {
//            Connection connection = dbConnection.getConnection();
//            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
//                    new JdbcConnection(connection));
//            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml",
//                    new ClassLoaderResourceAccessor(), database);
//         //   liquibase.update();
//            System.out.println("Миграции успешно выполнены!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        /**
//         * Инициализируем необходимые службы и запускаем приложение
//         * */
//        LiquibaseApp liquibaseApp = new LiquibaseApp();
//        try (Liquibase liquibase = liquibaseApp.initLiquibase()) {
//            liquibase.update();
//
//
//        } catch (LiquibaseException e) {
//            throw new RuntimeException(e);
//        }


//        String URL = "jdbc:postgresql://localhost:5432/wallet_service";
//        String USER_NAME = "wallet_service";
//        String USER_PASSWORD = "123";
//        Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD);
//
//        Statement st = connection.createStatement();
//        ResultSet rs = st.executeQuery("SELECT * FROM wallet.players");
//        Set<Player> allPlayers = new HashSet<>();
//        while(rs.next()){
//            String name = rs.getString("name");
//            String surname = rs.getString("surname");
//            String email = rs.getString("email");
//            allPlayers.add(new Player(name, surname, email));
//        }
//        System.out.println(allPlayers);


//        Statement st = connection.createStatement();
//        ResultSet s = st.executeQuery("SELECT surname FROM wallet.players WHERE name = 'Sveta'");
//        String s1 = s.getString(1);
//        System.out.println(s1);


      /*  Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id, name FROM wallet.players WHERE surname = 'K'");
        while (resultSet.next()){
           // int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println("row = " +   " " + name);
        }
//        String name = resultSet.getString("");
//        System.out.println("name = " + name);
        statement.close();*/
//        Player player = new Player("Sveta", "S", "omsk");
//        String getPlayerId = "SELECT id FROM wallet.players WHERE name = ?";
//        try (PreparedStatement prStatement = connection.prepareStatement(getPlayerId)) {
//            prStatement.setString(1, player.getFirstName());
//            //prStatement.setString(2, player.getLastName());
//            //prStatement.setString(3, player.getEmail());
//            ResultSet rs = prStatement.executeQuery();
//            int playerId = prStatement.executeQuery().getInt("id");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }



/*


        String URL = "jdbc:postgresql://localhost:5432/wallet_service";
        String USER_NAME = "wallet_service";
        String USER_PASSWORD = "123";
        Connection connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD);

       */
/* //STATEMENT
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id, name FROM test WHERE id = 1");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println("row = " + id + " " + name);
        }
        statement.close();

//      Statement  statement1 = connection.createStatement();
//      statement1.executeUpdate("INSERT INTO  test (id, name) VALUES (3, 'John')");
//      statement1.close();

      Statement statement2 = connection.createStatement();
      statement2.executeUpdate("DELETE FROM test WHERE id=2");*//*


        //PREPARED STATEMENT

        insertRecord(connection);
     //   createTable(connection);
*/

    }

    //PREPARED STATEMENT
    public static void insertRecord(Connection connection) throws SQLException {
        String insertDataSQL = "INSERT INTO  players (id, name, surname, email) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Denis");
        preparedStatement.setString(3, "Miagkov");
        preparedStatement.setString(4, "tomich-84@mail.ru");
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    //STATEMENT
    public static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS players (\n" +
                "\tid int PRIMARY KEY ,\n" +
                "\tname varchar(25) NOT NULL,\n" +
                "\tsurname varchar(25) NOT NULL,\n" +
                "\temail varchar(45) NOT NULL );";
        Statement statement = connection.createStatement();
        statement.executeUpdate(createTableSQL);
        statement.close();
    }


}
