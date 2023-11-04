package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.application.dto.EntryDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели игрока.
 */
public interface PlayerService {
    /**
     * Метод должен реализовывать регистрацию нового игрока
     *
     * @param playerDto ДТО игрока (данные. введенные пользователем при регистрации)
     * @return новый игрок
     */
    Player registerPlayer(PlayerDto playerDto);

    /**
     * Метод возвращает id игрока по его логину
     *
     * @param login логин игрока
     * @return int id игрока
     */
    public int getPlayerId(String login);

    /**
     * Метод возвращает игрока по его логину
     *
     * @param login логин игрока
     * @return игрок
     */
    public Player getPlayerByLogin(String login);

    /**
     * Метод возвращает комбинацию логин-пароль по логину игрока
     *
     * @param login логин игрока
     * @return комбинация логин-пароль
     */
    public Entry getEntryByLogin(String login);

}
