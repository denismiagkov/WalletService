package com.denismiagkov.walletservice.infrastructure.in.console.commands;

import com.denismiagkov.walletservice.infrastructure.in.console.Console;

/**
 * Класс описывает команду (действие) меню, завершающую использование приложения.
 * Является наследником класса {@link Command}.
 */
public class Finish extends Command {

    /**
     * Конструктор класса
     */
    public Finish(Console console) {
        super(console);
    }

    /**
     * Метод определяет название команды (действия)
     *
     * @return название команды (действия)
     */
    @Override
    public String getDescription() {
        return "Завершить работу";
    }

    /**
     * Метод вызывает метод {@link Console#finish()} для завершения работы приложения из стартового меню
     */
    @Override
    public void execute() {
        getConsole().finish();
    }

    /**
     * Метод вызывает метод {@link Console#finish(String, String)} для завершения работы приложения
     * из меню профиля.
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    @Override
    public void execute(String login, String password) {
        getConsole().finish(login, password);
    }

}
