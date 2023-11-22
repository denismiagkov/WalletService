package com.denismiagkov.walletservice.application.service.serviceImpl.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException{
    public PlayerAlreadyExistsException(String firstname, String lastName, String email) {
        super("Игрок " + firstname + " " + lastName + " (email: " + email + ") " +
                "уже зарегистрирован. Дублирование игроков не допускается");
    }
}
