package com.denismiagkov.walletservice.domain.model;

import lombok.*;

/**
 * Класс описывает игрока
 */
@Data
@RequiredArgsConstructor
public class Player {
    /**
     * Уникальный идентификатор игрока
     */
    int id;
    /**
     * Имя игрока
     */
    private final String firstName;

    /**
     * Фамилия игрока
     */
    private final String lastName;

    /**
     * Электронная почта игрока
     */
    private final String email;
    /**
     * Денежный счет
     */
    private Account account;

    public Player(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
