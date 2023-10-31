package com.denismiagkov.walletservice.infrastructure.in;

import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.infrastructure.in.exceptions.*;

public class DataValidator {

    /**
     * Метод проверяет корректность введенных пользователем числовых данных
     */
    public static boolean checkNumber(String input) {
        return input.matches("[0-9]+") && Integer.parseInt(input) > 0;
    }

    public static boolean checkLogin(String input) {
        return (!input.isEmpty());
    }

    public static boolean checkPassword(String input) {
        return (input.length() > 5);
    }

    public static boolean checkEmail(String input) {
        return input.matches("\\w+@\\w+\\.(com|ru)");
    }

    public static boolean checkName(String input) {
        return input.matches("[a-zA-Zа-яА-Я]+");
    }

    public static boolean checkRegistrationForm(PlayerDto playerDto) {
        if (!DataValidator.checkName(playerDto.getName())) {
            throw new IncorrectNameException();
        }
        if (!DataValidator.checkName(playerDto.getSurname())) {
            throw new IncorrectSurnameException();
        }
        if (!DataValidator.checkEmail(playerDto.getEmail())) {
            throw new IncorrectEmailException();
        }
        if (!DataValidator.checkLogin(playerDto.getLogin())) {
            throw new IncorrectLoginException();
        }
        if (!DataValidator.checkPassword(playerDto.getPassword())) {
            throw new IncorrectPasswordException();
        }
        return true;
    }
}
