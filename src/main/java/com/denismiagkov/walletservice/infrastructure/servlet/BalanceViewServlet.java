package com.denismiagkov.walletservice.infrastructure.servlet;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.AccountDto;
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("HEloo!!!");
        System.out.println(req.getParameter("login"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller = new Controller(new Service());
       // ObjectMapper mapper = new ObjectMapper();
      //  String login = mapper.readValue(req.getParameter("login"), String.class);
      //  String login = req.getParameter("login");
       // resp.getWriter().write(login + "xyz");
      //  System.out.println("LOGIN: " + login);
        AccountDto accountDto = controller.getCurrentBalance("login1");
        System.out.println("DTO: " + accountDto);
        String json = new ObjectMapper().writeValueAsString(accountDto);
        resp.getWriter().write(json);
    }

}
