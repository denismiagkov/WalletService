package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationDAOImplTest {

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
    OperationDAOImpl operationDAO;

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
        operationDAO = new OperationDAOImpl(dbConnection);
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
    void saveOperation() throws SQLException {
        connection.setAutoCommit(false);
        int size = operationDAO.getLog().size();
        Operation operation = new Operation(OperationType.CREDITING, new Timestamp(System.currentTimeMillis()),
                2, OperationStatus.SUCCESS);
        operationDAO.saveOperation(operation);
        assertEquals(size + 1, operationDAO.getLog().size());
        connection.rollback();
    }

    @Test
    void getLog() throws SQLException {
        connection.setAutoCommit(false);
        List<String> log = new ArrayList<>();
        log = operationDAO.getLog();
        assertNotEquals(0, log.size());
        connection.rollback();
    }

    @Test
    void getOperationId() {
    }
}