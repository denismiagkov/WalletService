package com.denismiagkov.walletservice.infrastructure.in.menu;

import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.in.commands.Finish;
import com.denismiagkov.walletservice.infrastructure.in.commands.OptionAuthorizePlayer;
import com.denismiagkov.walletservice.infrastructure.in.commands.OptionRegisterPlayer;

public class MainMenu extends Menu{
    public MainMenu(Console console) {
        super(console);
        list.add(new OptionRegisterPlayer(console));
        list.add(new OptionAuthorizePlayer(console));
        list.add(new Finish(console));
    }

}
