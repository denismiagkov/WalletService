package com.denismiagkov.walletservice.login_service;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;

public class User extends Entry {
    /**
     * Конструктор класса
     *
     * @param playerId
     * @param login
     * @param password
     */
    public User(int playerId, String login, String password) {
        super(playerId, login, password);
    }
}
