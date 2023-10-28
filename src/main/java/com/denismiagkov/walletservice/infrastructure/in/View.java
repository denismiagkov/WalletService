package com.denismiagkov.walletservice.infrastructure.in;

import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.in.menu.Menu;

/**
 * Интерфейс объявляет методы, которые должны реализовать интерфейсы как формы взаимодействия
 * между пользователем и приложением (командная строка, графический интерфейс и т.п.)
 */
public interface View {
    /**
     * Метод должен реализовывать вывод текстовой информации
     */
    void print(String text);

    /**
     * Метод должен начинать взаимодействие пользователя с приложением путем отображения стартового меню
     */
    void start();

    /**
     * Метод должен обновлять содержание отображаемой перед пользователем информации
     * в соответствии с его выбором. Метод должен принимать идентификатор (логин) и пароль игрока
     * для определения параметров последующей обработки информации и предоставления результатов
     *
     * @param login    логин игрока
     * @param password пароль игрока
     */
    void startProfile(String login, String password);
}
