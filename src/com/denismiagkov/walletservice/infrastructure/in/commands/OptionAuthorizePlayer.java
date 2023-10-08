package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Command;
import com.denismiagkov.walletservice.infrastructure.in.Console;

public class OptionAuthorizePlayer extends Command {

    public OptionAuthorizePlayer(Console console) {
        super(console);
    }

    @Override
    public String getDescription() {
        return "Войти в систему";
    }

    @Override
    public void execute() {
        getConsole().getDataToAuthorizePlayer();
    }

    @Override
    public void execute(String login, String password) {

    }
}
