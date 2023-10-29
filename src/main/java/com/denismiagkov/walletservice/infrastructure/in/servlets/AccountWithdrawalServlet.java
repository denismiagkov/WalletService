package com.denismiagkov.walletservice.infrastructure.in.servlets;

import annotations.Loggable;
import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/players/withdrawal")
public class AccountWithdrawalServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;
    AuthService authService;

    @Override
    public void init() throws ServletException {
        controller = new Controller(new Service());
        objectMapper = new ObjectMapper();
        authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Loggable
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jsonResponse = null;
        String token = authService.getTokenFromRequest(req);
        try {
            if (authService.validateAccessToken(token)) {
                String login = authService.getLoginFromToken(token);
                JsonNode jsonNode = objectMapper.readTree(req.getInputStream());
                String input = jsonNode.get("amount").asText();
                if (DataValidator.checkNumber(input)) {
                    BigDecimal amount = jsonNode.get("amount").decimalValue();
                    try {
                        controller.writeOffFunds(login, amount);
                        resp.setStatus(HttpServletResponse.SC_OK);
                        jsonResponse = "С вашего счета списана сумма " + amount + " денежных единиц";
                    } catch (RuntimeException e) {
                        jsonResponse = e.getMessage();
                        System.out.println("ERROR: " + jsonResponse);
                    }
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    jsonResponse = "Введено некорректное число!";
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                jsonResponse = "Не пройдена аутентификация!";
            }
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse = "Ошибка форматирования JSON";
        } finally {
            resp.getWriter().write("{\"message\": \"" + jsonResponse + "\"}");
        }
    }
}
