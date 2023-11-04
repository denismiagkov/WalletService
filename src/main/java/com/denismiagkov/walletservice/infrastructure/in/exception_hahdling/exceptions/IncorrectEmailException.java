package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions;

public class IncorrectEmailException extends RuntimeException{
    public IncorrectEmailException() {
        super("Ошибка ввода! Указан невалидный адрес электронной почты!");
    }
}
