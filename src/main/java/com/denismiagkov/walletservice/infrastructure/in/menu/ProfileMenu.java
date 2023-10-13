package com.denismiagkov.walletservice.infrastructure.in.menu;

import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.in.commands.*;

/**
 * Класс является наследником класса {@link Menu}. Определяет содержание меню программы (список доступных
 * для пользователя действий) после авторизаци игрока.
 */
public class ProfileMenu extends Menu {

    /**
     * Конструктор создает список команд меню после входа игрока в систему (авторизации).
     *
     * @see OptionShowCurrentBalance
     * @see OptionCallTopUpAccount
     * @see OptionCallWriteOffFunds
     * @see OptionShowTransactionHistory
     * @see Finish
     */
    public ProfileMenu(Console console) {
        super(console);
        list.add(new OptionShowCurrentBalance(console));
        list.add(new OptionCallTopUpAccount(console));
        list.add(new OptionCallWriteOffFunds(console));
        list.add(new OptionShowTransactionHistory(console));
        list.add(new Finish(console));
    }

}
