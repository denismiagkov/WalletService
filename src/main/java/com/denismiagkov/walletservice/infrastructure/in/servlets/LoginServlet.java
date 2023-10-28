package com.denismiagkov.walletservice.infrastructure.in.servlets;

import annotations.Loggable;
import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
import com.denismiagkov.walletservice.infrastructure.login_service.JwtRequest;
import com.denismiagkov.walletservice.infrastructure.login_service.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/authentication")
public class LoginServlet extends HttpServlet {

    private AuthService authService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
//        DatabaseConnection dbConnection = new DatabaseConnection();
//        try {
//            Connection connection = dbConnection.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        authService = new AuthService();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Loggable
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JwtRequest authRequest = objectMapper.readValue(req.getInputStream(), JwtRequest.class);
        JwtResponse authResponse = authService.login(authRequest);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.getOutputStream().write(objectMapper.writeValueAsBytes(authResponse));
    }
}
