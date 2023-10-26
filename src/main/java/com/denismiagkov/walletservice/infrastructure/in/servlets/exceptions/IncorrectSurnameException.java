package com.denismiagkov.walletservice.infrastructure.in.servlets.exceptions;

public class IncorrectSurnameException extends RuntimeException{
    public IncorrectSurnameException() {
        super("Ошибка ввода! Поле 'surname' может содержать только буквы!");
    }
}
