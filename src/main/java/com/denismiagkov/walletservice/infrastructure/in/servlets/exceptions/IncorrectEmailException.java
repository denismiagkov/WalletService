package com.denismiagkov.walletservice.infrastructure.in.servlets.exceptions;

public class IncorrectEmailException extends RuntimeException{
    public IncorrectEmailException() {
        super("Ошибка ввода! Указан невалидный адрес электронной почты!");
    }
}
