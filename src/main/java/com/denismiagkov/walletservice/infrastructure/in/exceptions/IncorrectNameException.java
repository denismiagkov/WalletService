package com.denismiagkov.walletservice.infrastructure.in.exceptions;

public class IncorrectNameException extends RuntimeException{
    public IncorrectNameException() {
        super("Ошибка ввода! Поле 'name' может содержать только буквы!");
    }
}
