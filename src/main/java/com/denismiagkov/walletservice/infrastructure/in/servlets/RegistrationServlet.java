package com.denismiagkov.walletservice.infrastructure.in.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.in.servlets.exceptions.IncorrectSurnameException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        controller = new Controller(new Service());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PlayerDto playerDto = objectMapper.readValue(req.getInputStream(), PlayerDto.class);
        try {
            System.out.println("point1");
            DataValidator.checkRegistrationForm(playerDto);
            System.out.println("point 2");
            controller.registerPlayer(playerDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\": \"Игрок " + playerDto.getName() + " " +
                    playerDto.getSurname() + " зарегистрирован\"}");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           // String s = e.getMessage();
            String jsonResponse  = "{\"message\": \"" + e.getMessage() + "\"}";
           System.out.println(jsonResponse);
           // System.out.println("2" + s);
            resp.getWriter().write(jsonResponse);
        }
    }
}
