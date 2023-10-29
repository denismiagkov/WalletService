package com.denismiagkov.walletservice.infrastructure.in.servlets;

import annotations.Loggable;
import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.TransactionDto;
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
import java.util.List;

@WebServlet("/players/transactions")
public class TransactionHistoryServlet extends HttpServlet {
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
        String login = req.getParameter("login");
        System.out.println(login);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.setContentType("application/json");
        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(
                (controller.getTransactionsHistory(login))));
    }

    @Loggable
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        byte[] responseJson = null;
        String token = authService.getTokenFromRequest(req);
        try {
            if (authService.validateAccessToken(token)) {
                String login = authService.getLoginFromToken(token);
                List<TransactionDto> transactionDtoList = controller.getTransactionsHistory(login);
                responseJson = objectMapper.writeValueAsBytes(transactionDtoList);
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
