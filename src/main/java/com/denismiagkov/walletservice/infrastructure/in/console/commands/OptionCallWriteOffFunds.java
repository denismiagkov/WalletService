package com.denismiagkov.walletservice.infrastructure.in.console.commands;

import com.denismiagkov.walletservice.infrastructure.in.console.Console;

/**
 * Класс описывает команду (действие) меню списать со счета игрока денежные средства.
 * Является наследником класса {@link Command}.
 */
public class OptionCallWriteOffFunds extends Command {

    /**
     * Конструтор класса
     * */
    public OptionCallWriteOffFunds(Console console) {
        super(console);
    }

    /**
     * Метод определяет название команды (действия)
     *
     * @return название команды (действия)
     */
    @Override
    public String getDescription() {
        return "Снять денежные средства";
    }


    /**
     * Метод вызывает метод {@link Console#callWriteOffFunds(String, String)} (String, String)}
     * для списания средств со счета.
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    @Override
    public void execute(String login, String password) {
        getConsole().callWriteOffFunds(login, password);
    }

    /**
     * Метод не используется
     * */
    @Override
    public void execute() {
    }
}
