package com.denismiagkov.walletservice.infrastructure.in.servlets.exceptions;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException() {
        System.out.println("Ошибка ввода! Поле \"login\" не может быть пустым!\n");
    }
}
