package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Command;
import com.denismiagkov.walletservice.infrastructure.in.Console;

public class OptionCallTopUpAccount extends Command {

    public OptionCallTopUpAccount(Console console) {
        super(console);
    }


    @Override
    public String getDescription() {
        return "Пополнить счет";
    }

    @Override
    public void execute(String login, String password) {
        getConsole().callTopUpAccount(login, password);
    }
    @Override
    public void execute() {
    }
}
