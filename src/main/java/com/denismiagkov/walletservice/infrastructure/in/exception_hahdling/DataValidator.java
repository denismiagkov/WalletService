package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling;

import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.*;


/**
 * Класс валидирует значения, переданные пользователем в веб-интерфейс приложения
 */
public class DataValidator {

    /**
     * Метод проверяет корректность введенных пользователем числовых данных
     */
    public static boolean checkNumber(String input) {
        return input.matches("[0-9]+") && Integer.parseInt(input) > 0;
    }

    /**
     * Метод проверяет корректность введенного логина на null
     */
    public static boolean checkLogin(String input) {
        return (!input.isEmpty());
    }

    /**
     * Метод проверяет корректность введенного пароля на минимальную длину
     */
    public static boolean checkPassword(String input) {
        return (input.length() > 5);
    }

    /**
     * Метод проверяет корректность ввода адреса электронной почты
     */
    public static boolean checkEmail(String input) {
        return input.matches("\\w+@\\w+\\.(com|ru)");
    }

    /**
     * Метод проверяет корректность ввода имени и фамилии пользователя на соответствие символам алфавита
     */
    public static boolean checkName(String input) {
        return input.matches("[a-zA-Zа-яА-Я]+");
    }

    /**
     * Интеграционный метод проверяет правильность заполнения формы для регистрации нового игрока
     *
     * @throws IncorrectNameException
     * @throws IncorrectSurnameException
     * @throws IncorrectEmailException
     * @throws IncorrectLoginException
     * @throws IncorrectPasswordException
     */
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

    /**
     * Метод проверяет корректность ввода суммы транзакции и выбрасывает соответствующее исключение
     *
     * @throws IncorrectInputNumberException
     */
    public static boolean checkTransactionAmount(String input) {
        if (!DataValidator.checkNumber(input)) {
            throw new IncorrectInputNumberException();
        }
        return true;
    }
}
