package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.containers.Test.DBConnectionProvider;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.liquibase.LiquibaseApp;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.management.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
    LiquibaseApp liquibase;
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
        liquibase = new LiquibaseApp(dbConnection);
        playerDAO = new PlayerDAOImpl();
        liquibase.start();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void savePlayer() throws SQLException {
        //playerDAO.savePlayer(new Player("Ivan", "Petrov", "ipetrov@mail.ru"));
        Set<Player> allPlayers = playerDAO.getAllPlayers();
        assertEquals(3, allPlayers.size());
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLES");
        System.out.println(rs);
//        st.executeUpdate("DELETE FROM wallet.players WHERE id = 3");
//        assertEquals(3, allPlayers.size());

    }

    @Test
    void saveEntry() {
    }

    @Test
    void getAllPlayers() {
       Set<Player> players =  playerDAO.getAllPlayers();
        System.out.println(players);
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