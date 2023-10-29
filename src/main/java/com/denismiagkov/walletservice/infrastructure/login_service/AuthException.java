package com.denismiagkov.walletservice.infrastructure.login_service;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super(message);
    }
}