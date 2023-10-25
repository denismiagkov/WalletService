package com.denismiagkov.walletservice.infrastructure.servlets;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.dto.EntryDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.login_service.JwtRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api")
public class AuthenticationServlet extends HttpServlet {
    Controller controller;
    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        controller = new Controller(new Service());
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      //  objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        JwtRequest jwtRequest = objectMapper.readValue(req.getInputStream(), JwtRequest.class);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
      //  objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        resp.getOutputStream().write(objectMapper.writeValueAsBytes(jwtRequest));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
