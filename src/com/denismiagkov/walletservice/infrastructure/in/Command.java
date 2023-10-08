package com.denismiagkov.walletservice.infrastructure.in;

public abstract class Command implements Option {
    private Console console;

    public Command(Console console) {
        this.console = console;
    }

    public Console getConsole() {
        return console;
    }

    @Override
    public abstract String getDescription();

    @Override
    public abstract void execute();
}
