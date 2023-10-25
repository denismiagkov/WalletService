package com.denismiagkov.walletservice.infrastructure.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.EntryDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.login_service.JwtRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        controller = new Controller(new Service());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JwtRequest jwtRequest = objectMapper.readValue(req.getInputStream(), JwtRequest.class);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.getOutputStream().write(objectMapper.writeValueAsBytes(jwtRequest));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
