package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Command;
import com.denismiagkov.walletservice.infrastructure.in.Console;

public class OptionShowCurrentBalance extends Command {

    public OptionShowCurrentBalance(Console console) {
        super(console);
    }

    @Override
    public String getDescription() {
        return "Показать текущий баланс";
    }

    @Override
    public void execute(String login, String password) {
        getConsole().showCurrentBalance(login, password);
    }
    @Override
    public void execute() {
    }
}
