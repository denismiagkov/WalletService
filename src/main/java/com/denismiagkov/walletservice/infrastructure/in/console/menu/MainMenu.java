package com.denismiagkov.walletservice.infrastructure.in.console.menu;

import com.denismiagkov.walletservice.infrastructure.in.console.Console;
import com.denismiagkov.walletservice.infrastructure.in.console.commands.Finish;
import com.denismiagkov.walletservice.infrastructure.in.console.commands.OptionAuthorizePlayer;
import com.denismiagkov.walletservice.infrastructure.in.console.commands.OptionRegisterPlayer;

/**
 * Класс является наследником класса {@link Menu}. Определяет содержание стартового меню программы (список доступных
 * для пользователя действий)
 * */
public class MainMenu extends Menu{

    /**
     * Конструктор создает список команд стартового меню.
     *
     * @see OptionRegisterPlayer
     * @see OptionAuthorizePlayer
     * @see Finish
     * */
    public MainMenu(Console console) {
        super(console);
        list.add(new OptionRegisterPlayer(console));
        list.add(new OptionAuthorizePlayer(console));
        list.add(new Finish(console));
    }

}
