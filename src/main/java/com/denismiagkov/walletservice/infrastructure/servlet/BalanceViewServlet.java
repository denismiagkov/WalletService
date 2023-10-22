package com.denismiagkov.walletservice.infrastructure.servlet;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("players/{id}/balance")
public class BalanceViewServlet extends HttpServlet {
    Controller controller;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        controller = new Controller(new Service());
        String login = req.getParameter("login");
        controller.getCurrentBalance(login);
        super.doGet(req, resp);
    }
}
