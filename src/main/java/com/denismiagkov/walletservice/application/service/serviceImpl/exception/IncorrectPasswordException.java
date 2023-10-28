package com.denismiagkov.walletservice.application.service.serviceImpl.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        System.out.println("Ошибка ввода! Неверный пароль!\n");
    }
}
