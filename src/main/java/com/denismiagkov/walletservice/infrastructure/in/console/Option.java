package com.denismiagkov.walletservice.infrastructure.in.console;

/**
 * Интерфейс объявляет методы, которые должны реализовать классы команд пользователя приложения
 */
public interface Option {

    /**
     * Метод должен возвращать название команды (директивы)
     */
    String getDescription();

    /**
     * Метод должен возвращать способ исполнения команды (директивы)
     */
    void execute();

    /**
     * Метод должен возвращать способ исполнения команды (директивы),
     * принимая в качестве параметра логин и пароль игрока
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    void execute(String login, String password);
}