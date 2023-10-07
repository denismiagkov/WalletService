package com.denismiagkov.walletservice.application.service.exception;

public class LoginIsNotUniqueException extends RuntimeException{
    public LoginIsNotUniqueException(String login) {
        System.out.println("Login '" + login + "' is not unique! Please, select another login.");
    }
}
