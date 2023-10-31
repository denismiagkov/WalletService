package com.denismiagkov.walletservice.infrastructure.in.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("Ошибка ввода! Поле 'password' должно составлять не менее 6 символов!");
    }
}
