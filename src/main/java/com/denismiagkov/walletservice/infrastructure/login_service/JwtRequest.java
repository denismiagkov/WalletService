package com.denismiagkov.walletservice.infrastructure.login_service;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на получение токена")
public class JwtRequest {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
