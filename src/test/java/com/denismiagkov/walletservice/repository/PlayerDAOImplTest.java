package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDAOImplTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:12.16"
    );
    DatabaseConnection dbConnection = new DatabaseConnection(
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword());
    Connection connection;

    {
        try {
            connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Liquibase liquibase;
    PlayerDAOImpl playerDAO;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
        playerDAO = new PlayerDAOImpl(dbConnection);
        liquibase = liquibaseApp.start();
    }


    //  Метод работает корретно при тестировании отдельно от других методов класса,
    //  но с ошибкой при запуске общего тестирования класса. Требуется дополнительная отладка
    @Test
    void savePlayer() throws SQLException {
        // Посмотрим, сколько записей о игроках содержится в базе данных до сохранения нового игрока
        Set<Player> allPlayers = playerDAO.getAllPlayers();
        assertEquals(2, allPlayers.size());
        // Сохраним в базе денных сведения о новом игроке
        connection.setAutoCommit(false);
        playerDAO.savePlayer(new Player("Ivan", "Petrov", "ipetrov@mail.ru"));
        // Посмотрим, сколько записей о игроках содержится в базе данных до сохранения нового игрока
        allPlayers = playerDAO.getAllPlayers();
        assertEquals(3, allPlayers.size());
        connection.rollback();
    }

    @Test
    void saveEntry() throws SQLException {
        connection.setAutoCommit(false);
        playerDAO.savePlayer(new Player("Nestor", "Sidorov", "nsidorov@mail.ru"));
        assertEquals(2, playerDAO.getAllEntries().size());
        Entry entry = new Entry(3, "login3", "password3");
        playerDAO.saveEntry(entry);
        assertEquals(3, playerDAO.getAllEntries().size());
        connection.rollback();
    }

    //  Метод требуется отладить: он работает корретно при тестировании отдельно от других методов класса,
    //  и с ошибкой при запуске общего тестирования класса
    @Test
    void getAllPlayers() throws SQLException {
        Set<Player> players = playerDAO.getAllPlayers();
        System.out.println(players);
        assertEquals(2, players.size());
    }

    @Test
    void getAllEntries() {
        assertEquals(2, playerDAO.getAllEntries().size());
        System.out.println(playerDAO.getAllEntries());
    }

    @Test
    void getPlayerId() {
        assertEquals(1, playerDAO.getPlayerId("login1"));
    }

    @Test
    void testGetPlayerId() {
        assertEquals(2, playerDAO.getPlayerId(new Player("Matt", "Miagkov", "kid@kidmail.ru")));
    }

}