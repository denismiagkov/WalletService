package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.init.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOImplTest {

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
    AccountDAOImpl accountDAO;

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
        accountDAO = new AccountDAOImpl(dbConnection);
        liquibase = liquibaseApp.start();
    }

    @Test
    void getCurrentBalance() throws SQLException, LiquibaseException {
        connection.setAutoCommit(false);
        assertEquals(new BigDecimal(0), accountDAO.getCurrentBalance(1));
        connection.rollback();
    }

    @Test
    void getTransactionHistory() throws SQLException {
        connection.setAutoCommit(false);
        assertEquals(2, accountDAO.getTransactionHistory(1).size());
        System.out.println(accountDAO.getTransactionHistory(2));
        connection.rollback();
    }

    // Метод работает корректно при отдельном вызове теста, но с ошибкой при запуске тестирования класса целиком.
    // Требуется дополнительная отладка
    @Test
    void increaseBalance() throws SQLException {
        connection.setAutoCommit(false);
        assertEquals(new BigDecimal(0), accountDAO.getCurrentBalance(2));
        accountDAO.increaseBalance(2, new BigDecimal(350));
        assertEquals(new BigDecimal(350), accountDAO.getCurrentBalance(2));
        connection.rollback();
    }

    // Метод работает корректно при отдельном вызове теста, но с ошибкой при запуске тестирования класса целиком.
    // Требуется дополнительняотладка
    @Test
    void decreaseBalance() throws SQLException {
        connection.setAutoCommit(false);
        accountDAO.increaseBalance(2, new BigDecimal(900));
        assertEquals(new BigDecimal(900), accountDAO.getCurrentBalance(2));
        accountDAO.decreaseBalance(2, new BigDecimal(230));
        assertEquals(new BigDecimal(670), accountDAO.getCurrentBalance(2));
        connection.rollback();
    }

    @Test
    void changeBalance() throws SQLException {
        connection.setAutoCommit(false);
        assertEquals(new BigDecimal(0), accountDAO.getCurrentBalance(2));
        accountDAO.changeBalance(2, new BigDecimal(1200));
        assertEquals(new BigDecimal(1200), accountDAO.getCurrentBalance(2));
        connection.rollback();
    }

    @Test
    void getAccountId() throws SQLException {
        connection.setAutoCommit(false);
        int playerId = 1;
        assertEquals(playerId, accountDAO.getAccountId(playerId));
        connection.rollback();
    }
}