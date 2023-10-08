package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Command;
import com.denismiagkov.walletservice.infrastructure.in.Console;

public class OptionRegisterPlayer extends Command {

    public OptionRegisterPlayer(Console console) {
        super(console);
    }

    @Override
    public String getDescription() {
        return "Зарегистрироваться";
    }

    @Override
    public void execute() {
        getConsole().getDataToRegisterPlayer();
    }

    @Override
    public void execute(String login, String password) {

    }
}
