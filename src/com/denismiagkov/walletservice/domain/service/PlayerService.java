package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Player;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели игрока.
 */
public interface PlayerService {
    /**
     * Метод должен реализовывать регистрацию нового игрока
     *
     * @param firstName имя игрока
     * @param lastName  фамилия игрока
     * @param email     электронная почта игрока
     * @param login     уникальный идентификатор игрока (логин)
     * @param password  идентифицирующий признак игрока (пароль)
     * @return новый игрок
     */

    Player registerPlayer(String firstName, String lastName, String email, String login, String password);

    /**
     * Метод должен реализовывать регистрацию нового игрока
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     * @return игрок, пытающийся войти в систему или совершить в ней определенное действие
     */
    Player authorizePlayer(String login, String password);
}
