package com.denismiagkov.walletservice.infrastructure.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/players")
public class BalanceViewServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
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
        PlayerDto playerDto = objectMapper.readValue(req.getInputStream(), PlayerDto.class);
        System.out.println(playerDto==null);
        System.out.println(playerDto);

        String responseJson = objectMapper.writeValueAsString(playerDto);
        resp.setContentType("application/json");
        resp.getWriter().write(responseJson);


//                controller.getCurrentBalance("login1");
//        System.out.println("DTO: " + accountDto);
//        String json = new ObjectMapper().writeValueAsString(accountDto);
//        resp.getWriter().write(json);
    }

}
