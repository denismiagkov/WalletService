package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.containers.Test.DBConnectionProvider;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDAOImplTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:12.16"
    );
    DatabaseConnection dbConnection;
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
         dbConnection = new DatabaseConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
         playerDAO = new PlayerDAOImpl();

    }

    @AfterEach
    void tearDown() {}

    @Test
    void savePlayer() {
        playerDAO.savePlayer(new Player("Ivan", "Petrov", "ipetrov@mail.ru"));
        Set<Player> allPlayers = playerDAO.getAllPlayers();
        assertEquals(1, allPlayers.size());
    }

    @Test
    void saveEntry() {
    }

    @Test
    void getAllPlayers() {
    }

    @Test
    void getAllEntries() {
    }

    @Test
    void getPlayerId() {
    }

    @Test
    void testGetPlayerId() {
    }
}