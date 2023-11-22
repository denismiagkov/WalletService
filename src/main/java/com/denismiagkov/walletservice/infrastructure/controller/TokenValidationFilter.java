package com.denismiagkov.walletservice.infrastructure.controller;

import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Фильтр-сервлет, реализующий валидацию токена игрока
 */
@Component
public class TokenValidationFilter implements Filter {
    /**
     * Сервис аутентификации
     */
    private final AuthService authService;
    private final String ENDPOINT = "/api/players/";
    private final String HEADER = "Authorization";
    private final String SESSION_ATTRIBUTE = "Login";
    private final String CONTENT_TYPE = "application/json";
    private final String ERROR_MESSAGE = "{" +
            "\"Info\": \"Authentication failed!\",\n" +
            "\"Exception\": \"%s\",\n" +
            "\"Cause\": \"%s\"" +
            "}";

    @Autowired
    public TokenValidationFilter(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Метод реализует валидацию токена игрока. В случае успеха запрос передается в рест-контроллер, в случае неудачи -
     * пользователю отправляется сообщение об ошибке.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            if (request.getServletPath().startsWith(ENDPOINT)) {
                String token = request.getHeader(HEADER);
                String login = authService.validateAccessToken(token);
                HttpSession session = request.getSession();
                session.setAttribute(SESSION_ATTRIBUTE, login);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setContentType(CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String message = String.format(ERROR_MESSAGE, e.getClass(), e.getMessage());
            response.getWriter().write(message);
        }
    }
}