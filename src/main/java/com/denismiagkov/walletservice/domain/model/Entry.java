package com.denismiagkov.walletservice.domain.model;

import java.util.Objects;
/**
 * Класс описывает уникальную комбинацию логин - пароль, используемую для аутентификаци игрока.
 * */
public class Entry {
    /**
     * Уникальный идентификатор игрока
     * */
    int playerId;

    /**
     * Логин игрока
     * */
    String login;

    /**
     * Пароль игрока
     * */
    String password;

    /**
     * Конструктор класса
     * */
    public Entry(int playerId, String login, String password) {
        this.playerId = playerId;
        this.login = login;
        this.password = password;
    }

    public Entry(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Метод toString()
     */
    @Override
    public String toString() {
        return "Entry{" +
                "playerId=" + playerId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Метод equals()
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(login, entry.login);
    }

    /**
     * Метод hashCode()
     * */
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
