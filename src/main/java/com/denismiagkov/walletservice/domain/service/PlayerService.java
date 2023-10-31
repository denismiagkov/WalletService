package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.application.dto.EntryDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
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

}
