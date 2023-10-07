package com.denismiagkov.walletservice.application.service.exception;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException(String str) {
        System.out.println("Player with login '" + str + "' isn't registered!");
    }
}
