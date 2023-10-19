package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    @AfterEach
    void tearDown() {
//        try {
//            liquibase.close();
//        } catch (LiquibaseException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    void saveAccount() throws SQLException {
        connection.setAutoCommit(false);
        int accountsNumber = 0;
        Statement statementBefore = connection.createStatement();
        ResultSet rsBefore = statementBefore.executeQuery("SELECT COUNT(*) FROM wallet.accounts");
        while (rsBefore.next()){
            accountsNumber = rsBefore.getInt(1);
        }
        assertEquals(2, accountsNumber);
        Player player = new Player("Ivan", "Petrov", "ipetrov@mail.ru");
        PlayerDAOImpl playerDAO = new PlayerDAOImpl(dbConnection);
        playerDAO.savePlayer(player);
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.createAccount(player);
        Statement statementAfter = connection.createStatement();
        ResultSet rsAfter = statementAfter.executeQuery("SELECT COUNT(*) FROM wallet.accounts");
        while (rsAfter.next()){
            accountsNumber = rsAfter.getInt(1);
        }
        assertEquals(3, accountsNumber);
        connection.rollback();
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

    @Test
    void increaseBalance() throws SQLException {
        connection.setAutoCommit(false);
        assertEquals(new BigDecimal(0), accountDAO.getCurrentBalance(2));
        accountDAO.increaseBalance(2, new BigDecimal(350));
        assertEquals(new BigDecimal(350), accountDAO.getCurrentBalance(2));
        connection.rollback();
    }

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