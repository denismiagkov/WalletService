package com.denismiagkov.walletservice.application.service.exception;

public class PlayerAlreadyExistsException extends RuntimeException{
    public PlayerAlreadyExistsException(String firstname, String lastName, String email) {
        System.out.println("Игрок " + firstname + " " + lastName + " (email: " + email + ") " +
                "уже зарегистрирован! Дублирование игроков не допускается!\n");
    }
}
