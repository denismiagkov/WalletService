package com.denismiagkov.walletservice.infrastructure.in.servlets;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.init.DatabaseConnection;
import com.denismiagkov.walletservice.infrastructure.liquibase.LiquibaseApp;
import liquibase.Liquibase;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.Container;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

import com.denismiagkov.walletservice.repository.PlayerDAOImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import liquibase.Liquibase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.given;

class RegistrationServletTest {

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
    String input = "{\n" +
            "    \"name\": \"Ivan\",\n" +
            "    \"surname\": \"Sergeev\",\n" +
            "    \"email\": \"iu@mail.ru\",\n" +
            "    \"login\": \"user1\",\n" +
            "    \"password\": \"1fhgr456\"\n" +
            "}";
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ServletInputStream inputStream;

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
    }

    @Test
    public void testDoPost_Correct_Input() throws ServletException, IOException, SQLException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        when(request.getInputStream()).thenReturn(inputStream);
        when(inputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            int offset = invocation.getArgument(1);
            int length = invocation.getArgument(2);
            return byteArrayInputStream.read(buffer, offset, length);
        });
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
//
        // Act
        connection.setAutoCommit(false);
        servlet.doPost(request, response);
//
//        // Assert
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        // assertEquals("{\"message\": \"Игрок Иван Петров зарегистрирован\"}", stringWriter.toString());
        connection.rollback();
    }


}



