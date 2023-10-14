import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.in.Console;

import java.sql.*;

/**
 * Это точка входа в программу
 * */
public class Main {
    /**
     * Это класс  main()
     * */
    public static void main(String[] args) throws SQLException {
        /**
         * Инициализируем необходимые службы и запускаем приложение
         * */
        Service service = new Service();
        Controller controller = new Controller(service);
        Console console = new Console(controller);
        console.start();

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
    public static void createTable(Connection connection) throws SQLException{
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
