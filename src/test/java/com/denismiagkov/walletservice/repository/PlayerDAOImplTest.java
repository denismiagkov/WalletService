package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.liquibase.LiquibaseApp;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
    Connection connection = dbConnection.getConnection();
    Liquibase liquibase;
    PlayerDAOImpl playerDAO;

    PlayerDAOImplTest() throws SQLException {
    }


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

    @AfterEach
    void tearDown() {
//        try {
//            liquibase.close();
//        } catch (LiquibaseException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    void savePlayer() throws SQLException {
        connection.setAutoCommit(false);
        playerDAO.savePlayer(new Player("Ivan", "Petrov", "ipetrov@mail.ru"));
        Set<Player> allPlayers = playerDAO.getAllPlayers();
        assertEquals(3, allPlayers.size());
        connection.rollback();
    }

    @Test
    void saveEntry() throws SQLException {
        connection.setAutoCommit(false);
        assertEquals(2, playerDAO.getAllEntries().size());
        Entry entry = new Entry(3, "login3", "password3");
        playerDAO.saveEntry(entry);
        assertEquals(3, playerDAO.getAllEntries().size());
        System.out.println(playerDAO.getAllEntries());
        connection.rollback();
    }

    @Test
    void getAllPlayers() throws SQLException {
        System.out.println(postgres.getJdbcUrl());
        System.out.println(connection.getMetaData());
        Set<Player> players = playerDAO.getAllPlayers();
        assertEquals(3, players.size());
    }

    @Test
    void getAllEntries() {
        assertEquals(2, playerDAO.getAllEntries().size());
        System.out.println(playerDAO.getAllEntries());
    }

    @Test
    void getPlayerId() {
        assertEquals(1, playerDAO.getPlayerId("login1", "password1"));
    }

    @Test
    void testGetPlayerId() {
        assertEquals(2, playerDAO.getPlayerId(new Player("Matt", "Miagkov", "kid@kidmail.ru")));
    }

//    @Test
//    void deletePlayer(int id) throws SQLException {
//        connection.setAutoCommit(false);
//        assertEquals(3, playerDAO.getAllPlayers().size());
//        playerDAO.deletePlayer(3);
//        assertEquals(2, playerDAO.getAllPlayers().size());
//        connection.rollback();
//    }
}