package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Console;

/**
 * Класс описывает команду (действие) меню показать историю транзакций на счете игрока.
 * Является наследником класса {@link Command}.
 */
public class OptionShowTransactionHistory extends Command {

    public OptionShowTransactionHistory(Console console) {
        super(console);
    }

    /**
     * Метод определяет название команды (действия)
     *
     * @return название команды (действия)
     */
    @Override
    public String getDescription() {
        return "Показать историю транзакций";
    }


    /**
     * Метод вызывает метод {@link Console#showTransactionHistory(String, String)} (String, String)}
     * показать историю транзакций.
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    @Override
    public void execute(String login, String password) {
        getConsole().showTransactionHistory(login, password);
    }

    /**
     * Метод не используется
     * */
    @Override
    public void execute() {
    }
}
