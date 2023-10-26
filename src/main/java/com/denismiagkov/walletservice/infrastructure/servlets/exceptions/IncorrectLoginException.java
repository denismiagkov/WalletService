package com.denismiagkov.walletservice.infrastructure.servlets.exceptions;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException() {
        System.out.println("Ошибка ввода! Поле \"login\" не может быть пустым!\n");
    }
}
