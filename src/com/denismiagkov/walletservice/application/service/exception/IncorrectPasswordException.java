package com.denismiagkov.walletservice.application.service.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        System.out.println("Entered password is incorrect!");
    }
}
