package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions;

public class IncorrectInputNumberException extends NumberFormatException{
    public IncorrectInputNumberException() {
        super("Ошибка ввода! Поле 'amount' может содержать только положительное число!");
    }
}
