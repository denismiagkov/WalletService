package com.denismiagkov.walletservice.infrastructure.login_service;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Токен")
public class JwtResponse {
    public String accessToken;
    public String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
