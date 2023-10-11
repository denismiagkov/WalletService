package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.domain.model.Player;

import java.util.Objects;
/**
 * Класс описывает уникальную комбинацию логин - пароль, используемую для аутентификаци игрока.
 * */
public class Entry {
    Player player;
    String login;
    String password;

    public Entry(Player player, String login, String password) {
        this.player = player;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "player=" + player +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(login, entry.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
