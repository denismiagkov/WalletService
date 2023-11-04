package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions;

public class IncorrectNameException extends RuntimeException{
    public IncorrectNameException() {
        super("Ошибка ввода! Поле 'name' может содержать только буквы!");
    }
}
