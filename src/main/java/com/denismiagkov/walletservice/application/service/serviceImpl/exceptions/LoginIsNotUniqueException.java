package com.denismiagkov.walletservice.application.service.serviceImpl.exceptions;

public class LoginIsNotUniqueException extends RuntimeException{
    public LoginIsNotUniqueException(String login) {
        System.out.println("Логин '" + login + "' не уникален! Пожалуйста, выберите другой логин.\n");
    }
}
