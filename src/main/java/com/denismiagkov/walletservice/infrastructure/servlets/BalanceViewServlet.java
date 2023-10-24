package com.denismiagkov.walletservice.infrastructure.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.dto.EntryDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
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

@WebServlet("/players")
public class BalanceViewServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;

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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("HEloo!!!");
        System.out.println(req.getParameter("login"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String login = objectMapper.readValue(req.getParameter("login"), String.class);
//        resp.getWriter().write(login + "xyz");
//        System.out.println("LOGIN: " + login);
//        PlayerDto playerDto = objectMapper.readValue(req.getInputStream(), PlayerDto.class);
//        System.out.println(playerDto==null);
//        System.out.println(playerDto);
        EntryDto entryDto = objectMapper.readValue(req.getInputStream(), EntryDto.class);
        System.out.println(entryDto==null);
        System.out.println(entryDto);

        String login = entryDto.getLogin();
        AccountDto accountDto = controller.getCurrentBalance(login);
        System.out.println(accountDto==null);
        String responseJson = objectMapper.writeValueAsString(accountDto);
        resp.setContentType("application/json");
        resp.getWriter().write(responseJson);


//                controller.getCurrentBalance("login1");
//        System.out.println("DTO: " + accountDto);
//        String json = new ObjectMapper().writeValueAsString(accountDto);
//        resp.getWriter().write(json);
    }

}
