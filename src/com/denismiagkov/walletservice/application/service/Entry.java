package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.domain.model.Player;

import java.util.Objects;

public class Entry {
    Player player;
    String login;
    String password;

    public Entry() {
    }

    public Entry(Player player, String login, String password) {
        this.player = player;
        this.login = login;
        this.password = password;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
