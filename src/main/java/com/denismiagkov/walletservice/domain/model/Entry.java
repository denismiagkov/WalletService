package com.denismiagkov.walletservice.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Класс описывает уникальную комбинацию логин - пароль, используемую для аутентификаци игрока.
 */
@Data
@EqualsAndHashCode(of = "login")
public class Entry {
    /**
     * Уникальный идентификатор игрока
     */
    private final int playerId;

    /**
     * Логин игрока
     */
    private final String login;

    /**
     * Пароль игрока
     */
    private final String password;
}
