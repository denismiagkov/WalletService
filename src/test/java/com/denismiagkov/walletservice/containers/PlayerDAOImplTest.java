package com.denismiagkov.walletservice.containers;

import com.denismiagkov.walletservice.PropertyFile;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Testcontainers
class PlayerDAOImplTest{

    @Container
    private static PostgresTestContainer container = new PostgresTestContainer();
    DatabaseConnection dbConnection;
    PropertyFile file;

    PlayerDAOImpl pdi;
//
//    private static final String DB_NAME = "testuser";
//    private static final String USERNAME = "testuser";
//    private static final String PASSWORD = "123";

    @BeforeEach
    public void setUp() {
        pdi = new PlayerDAOImpl();
       // dbConnection = new DatabaseConnection(new File("src/main/resources/application.properties"));
    }

    @Test
    public void testSavePlayer() throws SQLException {
//        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12.16")
//                .withDatabaseName(DB_NAME)
//                .withUsername(USERNAME)
//                .withPassword(PASSWORD)) {
//            postgres.start();
            PropertyFile file = new PropertyFile(new File("src/main/resources/application.properties"));
            file.setProperties("url", "localhost");
            file.setProperties("username", "testuser");
            file.setProperties("password", "testpassword");


            PostgresTestContainer container = new PostgresTestContainer();
            PostgreSQLContainer psc = PostgresTestContainer.getInstance();
            container.start();
            //System.out.println(container.getJdbcUrl());

            // Создаем таблицу players в тестовой БД
            try (Connection connection = getConnection(container);
                 PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players "
                         + "(id SERIAL PRIMARY KEY, name VARCHAR(255), surname VARCHAR(255), email VARCHAR(255))")) {
                statement.executeUpdate();
            }

            // Создаем объект Player для сохранения в БД
            Player player = new Player("John", "Doe", "john.doe@example.com");

            // Сохраняем игрока в БД

            pdi.savePlayer(player);

            // Проверяем, что игрок сохранен в БД
            try (Connection connection = getConnection(container);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM players WHERE id = ?")) {
                statement.setInt(1, player.getId());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    assertEquals(player.getFirstName(), resultSet.getString("name"));
                    assertEquals(player.getLastName(), resultSet.getString("surname"));
                    assertEquals(player.getEmail(), resultSet.getString("email"));
                } else {
                    fail("Player not found in database");
                }
            }
        }


    private Connection getConnection(PostgreSQLContainer<?> postgres) throws SQLException {
        return DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
    }
}
//    private Connection getConnection(PostgreSQLContainer<?> postgres) throws SQLException {
//        return DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
//    }


//
//    private PlayerDAOImpl pdi;
//
//    @Test
//    public void When_getAllPlayers_Expect_EmptyList() {
//        Set<Player> allPlayers = pdi.getAllPlayers();
//        assertEquals(2, allPlayers.size());
//    }
//
//
//
//
//
//
//
//
//
//
//  //  private RedisBackedCache underTest;
//
//    // container {
////    @Container
////    public GenericContainer postgres = new GenericContainer(DockerImageName.parse("postgres:12:16"))
////            .withExposedPorts(2345);
////
////    // }
////
////    @BeforeEach
////    public void setUp() {
////        String address = postgres.getHost();
////        Integer port = postgres.getFirstMappedPort();
////
////        // Now we have an address and port for Redis, no matter where it is running
////        underTest = new RedisBackedCache(address, port);
////    }
////
////    @Test
////    public void testSimplePutAndGet() {
////        underTest.put("test", "example");
////
////        String retrieved = underTest.get("test");
////        assertThat(retrieved).isEqualTo("example");
////    }
////
//
//    @Test
//    void savePlayer() {
//    }
//
//    @Test
//    void saveEntry() {
//    }
//
//    @Test
//    void getAllPlayers() {
//    }
//
//    @Test
//    void getAllEntries() {
//    }
//
//    @Test
//    void getPlayerId() {
//    }
//
//    @Test
//    void testGetPlayerId() {
//    }
