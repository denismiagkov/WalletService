package com.denismiagkov.walletservice.infrastructure.in.servlets.exceptions;

public class IncorrectEmailException extends RuntimeException{
    public IncorrectEmailException() {
        System.out.println("Ошибка ввода! Указан невалидный адрес электронной почты!\n");
    }
}
