package com.denismiagkov.walletservice.infrastructure.in.servlets.exceptions;

public class IncorrectNameException extends RuntimeException{
    public IncorrectNameException() {
        System.out.println("Ошибка ввода! Поле \"name\" может содержать только буквы!\n");
    }
}
