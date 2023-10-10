package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Console;

/**
 * Класс описывает команду (действие) входа пользователя в приложение.
 * Является наследником класса {@link Command}.
 */
public class OptionAuthorizePlayer extends Command {

    public OptionAuthorizePlayer(Console console) {
        super(console);
    }

    @Override
    public String getDescription() {
        return "Войти в систему";
    }


    /**
     * Метод вызывает метод {@link Console#getDataToAuthorizePlayer()} (String, String)}
     * для авторизации пользователя.
     */
    @Override
    public void execute() {
        getConsole().getDataToAuthorizePlayer();
    }

    /**
     * Метод не используется
     */
    @Override
    public void execute(String login, String password) {

    }
}
