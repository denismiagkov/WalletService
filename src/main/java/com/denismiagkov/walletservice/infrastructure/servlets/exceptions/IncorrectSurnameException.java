package com.denismiagkov.walletservice.infrastructure.servlets.exceptions;

public class IncorrectSurnameException extends RuntimeException{
    public IncorrectSurnameException() {
        System.out.println("Ошибка ввода! Поле \"surname\" может содержать только буквы!\n");
    }
}
