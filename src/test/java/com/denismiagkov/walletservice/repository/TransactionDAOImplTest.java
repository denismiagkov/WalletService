package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.init.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDAOImplTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:12.16"
    );
    DatabaseConnection dbConnection = new DatabaseConnection(
            postgres.getJdbcUrl(),
            postgres.getUsername(),
            postgres.getPassword(),
            driver);
    Connection connection;

    {
        try {
            connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Liquibase liquibase;
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
    void setUp() {
        LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
        transactionDAO = new TransactionDAOImpl(dbConnection);
        liquibase = liquibaseApp.start();
    }

//    @AfterEach
//    void tearDown() {
//        try {
//            liquibase.close();
//        } catch (LiquibaseException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test
    void saveTransaction() throws SQLException {
        connection.setAutoCommit(false);
        AccountDAOImpl accountDAO = new AccountDAOImpl(dbConnection);
        assertEquals(2, accountDAO.getTransactionHistory(1).size());
        Transaction transaction = new Transaction(1, new Timestamp(System.currentTimeMillis()),
                TransactionType.CREDIT, new BigDecimal(540));
        transactionDAO.saveTransaction(transaction);
        assertEquals(3, accountDAO.getTransactionHistory(1).size());
        connection.rollback();
    }

    @Test
    void getTransactionId() throws SQLException {
        connection.setAutoCommit(false);
        Transaction transaction = new Transaction(1, new Timestamp(System.currentTimeMillis()),
                TransactionType.CREDIT, new BigDecimal(540));
        transactionDAO.saveTransaction(transaction);
        assertNotEquals(0, transactionDAO.getTransactionId(transaction));
        connection.rollback();
    }
}