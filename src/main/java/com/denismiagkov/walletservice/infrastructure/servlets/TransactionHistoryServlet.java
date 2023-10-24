package com.denismiagkov.walletservice.infrastructure.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.TransactionDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionHistoryServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        System.out.println(login);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.setContentType("application/json");
        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(
                (controller.getTransactionsHistory(login))));
//
//        List<TransactionDto> transactionDtoList = controller.getTransactionsHistory(login);
//        String responseJson = objectMapper.writeValueAsString(transactionDtoList);
//        resp.setContentType("application/json");
//        resp.getWriter().write(responseJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        System.out.println("dbConnection right");
        try {
            Connection connection = dbConnection.getConnection();
            System.out.println("connection right");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        controller = new Controller(new Service());
        objectMapper = new ObjectMapper();
    }
}
