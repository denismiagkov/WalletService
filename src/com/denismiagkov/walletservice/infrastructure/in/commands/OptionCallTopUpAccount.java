package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Console;

/**
 * Класс описывает команду (действие) меню пополнить денежный счет игрока.
 * Является наследником класса {@link Command}.
 */
public class OptionCallTopUpAccount extends Command {

    public OptionCallTopUpAccount(Console console) {
        super(console);
    }

    /**
     * Метод определяет название команды (действия)
     *
     * @return название команды (действия)
     */
    @Override
    public String getDescription() {
        return "Пополнить счет";
    }

    /**
     * Метод вызывает метод {@link Console#callTopUpAccount(String, String)} (String, String)}
     * для пополнения счета игрока.
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    @Override
    public void execute(String login, String password) {
        getConsole().callTopUpAccount(login, password);
    }

    /**
     * Метод не используется
     * */
    @Override
    public void execute() {
    }
}
