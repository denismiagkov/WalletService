package com.denismiagkov.walletservice.application.service.exception;

public class NotUniqueTransactionIdException extends RuntimeException{
    public NotUniqueTransactionIdException(String str) {
        System.out.println("ID '" + str + "' is not unique! Transaction is not permitted!");
    }
}
