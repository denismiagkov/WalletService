package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Console;

/**
 * Класс описывает команду (действие) меню показать текущий баланс счета игрока.
 * Является наследником класса {@link Command}.
 */
public class OptionShowCurrentBalance extends Command {

    /**
     * Конструтор класса
     * */
    public OptionShowCurrentBalance(Console console) {
        super(console);
    }

    /**
     * Метод определяет название команды (действия)
     *
     * @return название команды (действия)
     */
    @Override
    public String getDescription() {
        return "Показать текущий баланс";
    }

    /**
     * Метод вызывает метод {@link Console#showCurrentBalance(String, String)} (String, String)}
     * показать текущий баланс счета игрока.
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    @Override
    public void execute(String login, String password) {
        getConsole().showCurrentBalance(login, password);
    }

    /**
     * Метод не используется
     * */
    @Override
    public void execute() {
    }
}
