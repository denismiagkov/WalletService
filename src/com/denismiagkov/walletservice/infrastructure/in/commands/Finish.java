package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Command;
import com.denismiagkov.walletservice.infrastructure.in.Console;

public class Finish extends Command {

    public Finish(Console console) {
        super(console);
    }

    @Override
    public String getDescription() {
        return "Завершить работу";
    }

    @Override
    public void execute() {
    }

    @Override
    public void execute(String login, String password) {
        getConsole().finish(login, password);
    }

}
