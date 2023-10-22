package com.denismiagkov.walletservice.login_service;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super(message);
    }
}