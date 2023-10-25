package com.denismiagkov.walletservice.login_service;

import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private final Service service;
    private final Map<String, String> refreshStorage;
    private final JwtProvider jwtProvider;

    public AuthService() {
        this.service = new Service();
        this.refreshStorage = new HashMap<>();
        this.jwtProvider = new JwtProvider();
    }

    public JwtResponse login(JwtRequest authRequest) {
        Entry entry = service.getEntryByLogin(authRequest.getLogin());
        if (entry.getPassword().equals(authRequest.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(entry);
            String refreshToken = jwtProvider.generateRefreshToken(entry);
            refreshStorage.put(entry.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                Entry entry = service.getEntryByLogin(login);
                String accessToken = jwtProvider.generateAccessToken(entry);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                Entry entry = service.getEntryByLogin(login);
                String accessToken = jwtProvider.generateAccessToken(entry);
                String newRefreshToken = jwtProvider.generateRefreshToken(entry);
                refreshStorage.put(entry.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

//    public JwtAuthentication getAuthInfo() {
//        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
//    }

}

