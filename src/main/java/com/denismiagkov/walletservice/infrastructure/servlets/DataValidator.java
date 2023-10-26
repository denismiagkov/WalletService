package com.denismiagkov.walletservice.infrastructure.servlets;

import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.dto.PlayerMapper;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.servlets.exceptions.*;

public class DataValidator {
    private final String input;

    public DataValidator(String input) {
        this.input = input;
    }

    /**
     * Метод проверяет корректность введенных пользователем числовых данных
     */
    public static boolean checkNumber(String input) {
        return input.matches("[0-9]+") && Integer.parseInt(input) > 0;
    }

    public static boolean checkNotEmptyField(String input) {
        return (!input.isEmpty());
    }

    public static boolean checkEmail(String input) {
        return input.matches("\\w+@\\w+\\.(com|ru)");
    }

    public static boolean checkName(String input) {
        return input.matches("[a-zA-Zа-яА-Я]+");
    }

    public static boolean checkRegistrationForm(PlayerDto playerDto) throws RuntimeException {
        if (!DataValidator.checkName(playerDto.getName())) {
            throw new IncorrectNameException();
        }
        if (!DataValidator.checkName(playerDto.getSurname())) {
            throw new IncorrectSurnameException();
        }
        if (!DataValidator.checkEmail(playerDto.getEmail())) {
            throw new IncorrectEmailException();
        }
        if (!DataValidator.checkNotEmptyField(playerDto.getLogin())) {
            throw new IncorrectLoginException();
        }
        if (!DataValidator.checkNotEmptyField(playerDto.getPassword())) {
            throw new IncorrectPasswordException();
        }
        return true;
    }
}
