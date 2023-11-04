package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException() {
        super("Ошибка ввода! Поле 'login' не может быть пустым!");
    }
}
