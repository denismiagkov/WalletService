package com.denismiagkov.walletservice.application.service.exception;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException(String str) {
        System.out.println("Ошибка ввода! Игрок с логином '" + str + "' не зарегистрирован!\n");
    }
}
