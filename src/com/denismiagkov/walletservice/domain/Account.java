package com.denismiagkov.walletservice.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    String number;
    BigDecimal balance;
    Player player;

    public Account() {
    }

    public Account(String number, BigDecimal balance, Player player) {
        this.number = number;
        this.balance = balance;
        this.player = player;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", balance=" + balance +
                ", player=" + player +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number == account.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
