package com.denismiagkov.walletservice.infrastructure.servlets;

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
        return input.isEmpty();
    }


}
