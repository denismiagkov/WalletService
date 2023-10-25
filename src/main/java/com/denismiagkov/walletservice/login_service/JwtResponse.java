package com.denismiagkov.walletservice.login_service;

public class JwtResponse {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
