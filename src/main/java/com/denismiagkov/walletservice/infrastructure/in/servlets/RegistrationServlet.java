package com.denismiagkov.walletservice.infrastructure.in.servlets;

import annotations.Loggable;
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

    @Loggable
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jsonResponse = null;
        PlayerDto playerDto = objectMapper.readValue(req.getInputStream(), PlayerDto.class);
        try {
            DataValidator.checkRegistrationForm(playerDto);
            controller.registerPlayer(playerDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            jsonResponse = "{\"message\": \"Игрок " + playerDto.getName() + " " +
                    playerDto.getSurname() + " зарегистрирован\"}";
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse = "{\"message\": \"" + e.getMessage() + "\"}";
        } finally {
            resp.getWriter().write(jsonResponse);
        }
    }
}
