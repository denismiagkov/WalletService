package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Command;
import com.denismiagkov.walletservice.infrastructure.in.Console;

public class OptionShowTransactionHistory extends Command {

    public OptionShowTransactionHistory(Console console) {
        super(console);
    }

    @Override
    public String getDescription() {
        return "Показать историю транзакций";
    }

    @Override
    public void execute(String login, String password) {
        getConsole().showTransactionHistory(login, password);
    }
    @Override
    public void execute() {
    }
}
