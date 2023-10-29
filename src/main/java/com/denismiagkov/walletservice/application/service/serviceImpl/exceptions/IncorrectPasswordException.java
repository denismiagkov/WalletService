package com.denismiagkov.walletservice.application.service.serviceImpl.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("Ошибка ввода! Неверный пароль!");
    }
}
