package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Console;

/**
 * Класс описывает команду (действие) меню, завершающую использование приложения.
 * Является наследником класса {@link Command}.
 */
public class Finish extends Command {

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
     * Метод не используется
     * */
    @Override
    public void execute() {
    }

    /**
     * Метод вызывает метод {@link Console#finish(String, String)} для завершения работы приложения.
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    @Override
    public void execute(String login, String password) {
        getConsole().finish(login, password);
    }

}
