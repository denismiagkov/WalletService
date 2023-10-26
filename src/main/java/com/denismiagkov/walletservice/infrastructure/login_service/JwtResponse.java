package com.denismiagkov.walletservice.infrastructure.login_service;

public class JwtResponse {
    public final String type = "Bearer";
    public String accessToken;
    public String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
