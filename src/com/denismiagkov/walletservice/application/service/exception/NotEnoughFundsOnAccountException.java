package com.denismiagkov.walletservice.application.service.exception;

public class NotEnoughFundsOnAccountException extends RuntimeException{
    public NotEnoughFundsOnAccountException() {
        System.out.println("Недостаточно денежных средств на счете для совершения транзакции! " +
                "Пожалуйста, пополните ваш баланс!\n");
    }
}
