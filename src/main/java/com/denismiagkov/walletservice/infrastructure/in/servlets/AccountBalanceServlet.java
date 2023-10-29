package com.denismiagkov.walletservice.infrastructure.in.servlets;

import annotations.Loggable;
import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/players/balance")
public class AccountBalanceServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;
    AuthService authService;

    @Override
    public void init() throws ServletException {
        controller = new Controller(new Service());
        objectMapper = new ObjectMapper();
        authService = new AuthService();
    }

    @Loggable
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        byte[] responseJson = null;
        String token = authService.getTokenFromRequest(req);
        String login = authService.getLoginFromToken(token);
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
