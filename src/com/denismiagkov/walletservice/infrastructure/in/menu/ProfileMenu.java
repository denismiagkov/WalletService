package com.denismiagkov.walletservice.infrastructure.in.menu;

import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.in.commands.*;

public class ProfileMenu extends Menu{
    public ProfileMenu(Console console) {
        super(console);
        list.add(new OptionShowCurrentBalance(console));
        list.add(new OptionCallTopUpAccount(console));
        list.add(new OptionСallWriteOffFunds(console));
        list.add(new OptionShowTransactionHistory(console));
        list.add(new Finish(console));
    }

}
