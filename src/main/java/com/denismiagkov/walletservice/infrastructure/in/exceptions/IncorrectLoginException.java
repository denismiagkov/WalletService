package com.denismiagkov.walletservice.infrastructure.in.exceptions;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException() {
        super("Ошибка ввода! Поле 'login' не может быть пустым!");
    }
}
