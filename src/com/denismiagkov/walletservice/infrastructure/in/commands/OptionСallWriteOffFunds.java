package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Command;
import com.denismiagkov.walletservice.infrastructure.in.Console;

public class OptionСallWriteOffFunds extends Command {

    public OptionСallWriteOffFunds(Console console) {
        super(console);
    }

    @Override
    public String getDescription() {
        return "Снять денежные средства";
    }

    @Override
    public void execute(String login, String password) {
        getConsole().callWriteOffFunds(login, password);
    }
    @Override
    public void execute() {
    }
}
