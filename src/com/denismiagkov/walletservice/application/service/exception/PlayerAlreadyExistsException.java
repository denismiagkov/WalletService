package com.denismiagkov.walletservice.application.service.exception;

public class PlayerAlreadyExistsException extends RuntimeException{
    public PlayerAlreadyExistsException(String firstname, String lastName, String email) {
        System.out.println("The player " + firstname + " " + lastName + " (email: " + email + ") " +
                "has been registered! Duplication of players is not permitted.");
    }
}
