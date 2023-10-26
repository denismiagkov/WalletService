package com.denismiagkov.walletservice.infrastructure.in.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.service.Service;
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
            DataValidator.checkRegistrationForm(playerDto);
            controller.registerPlayer(playerDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\": \"Игрок " + playerDto.getName() + " " +
                    playerDto.getSurname() + " зарегистрирован\"}");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            String jsonResponse  = "{\"message\": \"" + e.getMessage() + "\"}";
            System.out.println("1" + jsonResponse);
            System.out.println("2" + e.getMessage());
            resp.getWriter().write(jsonResponse);
        }
    }
}
