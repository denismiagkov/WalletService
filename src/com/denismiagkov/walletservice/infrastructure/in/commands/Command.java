package com.denismiagkov.walletservice.infrastructure.in.commands;

import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.in.Option;

/**
 * Родительский класс для команд (директив, опций), выбор которых предоставлен пользователю в процессе
 * взаимодействия с приложением в терминале.
 */
public abstract class Command implements Option {
    private Console console;

    public Command(Console console) {
        this.console = console;
    }

    public Console getConsole() {
        return console;
    }

    /**
     * Метод предоставляет название (краткое описание) команды (директивы), которую пользователь имеет возможность
     * выбрать путем ввода числового значения в терминале для продолжения взаимодействия с приложением.
     *
     * @return название(краткое описание команды)
     */
    @Override
    public abstract String getDescription();

    @Override
    public abstract void execute();
}
