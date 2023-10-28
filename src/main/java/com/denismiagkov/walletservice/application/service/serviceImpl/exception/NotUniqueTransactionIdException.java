package com.denismiagkov.walletservice.application.service.serviceImpl.exception;

public class NotUniqueTransactionIdException extends RuntimeException{
    public NotUniqueTransactionIdException(String str) {
        System.out.println("ID операции '" + str + "' не является уникальным! Транзакция отклонена!\n");
    }
}
