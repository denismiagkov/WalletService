package com.denismiagkov.walletservice.infrastructure.in.servlets.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        System.out.println("Ошибка ввода! Поле \"password\" не может быть пустым!\n");
    }
}
