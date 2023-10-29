package com.denismiagkov.walletservice.infrastructure.in.servlets;

import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
import com.denismiagkov.walletservice.init.DatabaseConnection;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import liquibase.Liquibase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.testcontainers.containers.PostgreSQLContainer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.given;

class TransactionHistoryServletTest {
    RegistrationServlet servlet;
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

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsb2dpbjEiLCJleHAiOjE2OTgzODgxMDAsInVzZXJJZCI6MX0.J74p4eczxQ-" +
            "wwfaTSl0zWrStYAs-XejjfQuJPYgiP6nXe7SMrKbRRH-Z238nfl0bRpHNBEyl9QTOMQuSNnGdmg";

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ServletInputStream inputStream;
    AuthService authService;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @BeforeEach
    void setUp() throws IOException, ServletException {
        LiquibaseApp liquibaseApp = new LiquibaseApp(dbConnection);
        playerDAO = new PlayerDAOImpl(dbConnection);
        liquibase = liquibaseApp.start();
        MockitoAnnotations.initMocks(this);
        servlet = new RegistrationServlet();
        servlet.init();
        authService = new AuthService();
    }


    @Test
    void doPost() {
        given(playerDAO.getPlayerById(1))
        (authService.getTokenFromRequest(request)).willReturn(token);
    }
}