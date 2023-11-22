package com.denismiagkov.walletservice.application.service.serviceImpl.exceptions;

public class NotEnoughFundsOnAccountException extends RuntimeException{
    public NotEnoughFundsOnAccountException() {
        super("Недостаточно денежных средств на счете для совершения транзакции");
    }
}
