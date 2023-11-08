package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class AccountDAOImplTest {

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
    void setUp() throws SQLException {
        liquibaseApp.start();
        accountDAO = new AccountDAOImpl(dbConnection);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
    }

    @Test
    void saveAccount() {
    }

    @Test
    void getTransactionHistory_Values_of_returned_list_are_correct() {
        List<Transaction> transactionHistory = accountDAO.getTransactionHistory(1);
        assertAll(
                () -> assertNotNull(transactionHistory),
                () -> assertEquals(2, accountDAO.getTransactionHistory(1).size()),
                () -> assertEquals(TransactionType.CREDIT, transactionHistory.get(0).getType()),
                () -> assertEquals(BigDecimal.valueOf(750), transactionHistory.get(1).getAmount())
        );

    }

    @Test
    void increaseBalance_Balance_Increases_On_350() {
        assertEquals(new BigDecimal(0), accountDAO.getCurrentBalance(2).getBalance());
        accountDAO.increaseBalance(2, new BigDecimal(350));
        assertEquals(new BigDecimal(350), accountDAO.getCurrentBalance(2).getBalance());
    }

    @Test
    void decreaseBalance_Balance_Decreases_On_230() {
        accountDAO.increaseBalance(2, new BigDecimal(900));
        assertEquals(new BigDecimal(900), accountDAO.getCurrentBalance(2).getBalance());
        accountDAO.decreaseBalance(2, new BigDecimal(230));
        assertEquals(new BigDecimal(670), accountDAO.getCurrentBalance(2).getBalance());
    }

    @Test
    void changeBalance_Balance_Changed_From_0_To_1200() {
        assertEquals(new BigDecimal(0), accountDAO.getCurrentBalance(2).getBalance());
        accountDAO.changeBalance(2, new BigDecimal(1200));
        assertEquals(new BigDecimal(1200), accountDAO.getCurrentBalance(2).getBalance());
    }

    @Test
    void getAccountId() {
        int playerId = 1;
        assertEquals(playerId, accountDAO.getAccountId(playerId));
    }

    @Test
    void getCurrentBalance_Balance_equals_0() {
        assertEquals(new BigDecimal(0), accountDAO.getCurrentBalance(1).getBalance());
    }
}