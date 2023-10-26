package com.denismiagkov.walletservice.application.service.serviceImpl.exceptions;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException(String str) {
        super("Ошибка ввода! Игрок с логином '" + str + "' не зарегистрирован!");
    }
}
