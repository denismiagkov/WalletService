package com.denismiagkov.walletservice.application.service.exception;

public class NotEnoughFundsOnAccountException extends RuntimeException{
    public NotEnoughFundsOnAccountException() {
        System.out.println("There are not enough funds on the account for fulfilling transaction! " +
                "Please, top up your account.");
    }
}
