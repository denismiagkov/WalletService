package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOImplTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:12.16"
    );

    DatabaseConnection dbConnection = new DatabaseConnection(
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword(),
            postgres.getDriverClassName());
    Connection connection;

    {
        try {
            connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
    TransactionDAOImpl transactionDAO;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        liquibaseApp.start();
        transactionDAO = new TransactionDAOImpl(dbConnection);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }

    @Test
    void saveTransaction_Number_Of_Transactions_Increases_For_1() {
        AccountDAOImpl accountDAO = new AccountDAOImpl(dbConnection);
        assertEquals(2, accountDAO.getTransactionHistory(1).size());
        Transaction transaction = new Transaction(1, new Timestamp(System.currentTimeMillis()),
                TransactionType.CREDIT, new BigDecimal(540));
        transactionDAO.saveTransaction(transaction);
        assertEquals(3, accountDAO.getTransactionHistory(1).size());
    }
}