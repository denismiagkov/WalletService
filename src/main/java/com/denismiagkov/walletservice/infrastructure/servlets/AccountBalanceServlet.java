package com.denismiagkov.walletservice.infrastructure.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.login_service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/players/balance")
public class AccountBalanceServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;
    AuthService authService;

    @Override
    public void init() throws ServletException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            Connection connection = dbConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        controller = new Controller(new Service());
        objectMapper = new ObjectMapper();
        authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        byte[] responseJson = null;
        String token = req.getHeader("Authorization").substring(7);
        String login = objectMapper.readTree(req.getInputStream()).get("login").asText();
        try {
            if (authService.validateAccessToken(token)) {
                AccountDto accountDto = controller.getCurrentBalance(login);
                responseJson = objectMapper.writeValueAsBytes(accountDto);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                responseJson = objectMapper.writeValueAsBytes("Не пройдена аутентификация!");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            resp.getOutputStream().write(responseJson);
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Ошибка форматирования JSON");
        }
    }
}
