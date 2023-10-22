package com.denismiagkov.walletservice.login_service;

import com.denismiagkov.walletservice.application.service.Service;
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
            final User user = service.getPlayerByLogin(authRequest.getLogin())
        //            .orElseThrow(() -> new AuthException("Пользователь не найден"));
            if (user.getPassword().equals(authRequest.getPassword())) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String refreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getLogin(), refreshToken);
                return new JwtResponse(accessToken, refreshToken);
            } else {
                throw new AuthException("Неправильный пароль");
            }
        }

        public JwtResponse getAccessToken(String refreshToken) {
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
                final String login = claims.getSubject();
                final String saveRefreshToken = refreshStorage.get(login);
                if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                    final User user = service.getPlayerByLogin(login)
            //                .orElseThrow(() -> new AuthException("Пользователь не найден"));
                    final String accessToken = jwtProvider.generateAccessToken(user);
                    return new JwtResponse(accessToken, null);
                }
            }
            return new JwtResponse(null, null);
        }

        public JwtResponse refresh(String refreshToken) {
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
                final String login = claims.getSubject();
                final String saveRefreshToken = refreshStorage.get(login);
                if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                    final User user = service.getPlayerByLogin(login)
          //                  .orElseThrow(() -> new AuthException("Пользователь не найден"));
                    final String accessToken = jwtProvider.generateAccessToken(user);
                    final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                    refreshStorage.put(user.getLogin(), newRefreshToken);
                    return new JwtResponse(accessToken, newRefreshToken);
                }
            }
            throw new AuthException("Невалидный JWT токен");
        }

        public JwtAuthentication getAuthInfo() {
            return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        }

    }
}
