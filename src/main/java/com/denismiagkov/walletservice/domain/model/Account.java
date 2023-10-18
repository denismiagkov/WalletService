package com.denismiagkov.walletservice.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс описывает денежный счет игрока
 */
public class Account {
    /**
     * Уникальный идентификатор счета
     */
    int id;
    /**
     * Номер счета
     */
    String number;
    /**
     * Баланс счета
     */
    BigDecimal balance;

    /**
     * Идентификатор игрока - владельца счета
     */
    int playerId;

    /**
     * Конструктор класса
     * */
    public Account(String number) {
        this.number = number;
        this.balance = new BigDecimal(0);
    }

    /**
     * Возвращает id счета
     * */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает id счета
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает номер счета
     */
    public String getNumber() {
        return number;
    }


    /**
     * Метод возвращает баланс счета
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Метод устанавливает баланс счета
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Метод toString()
     */
    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", balance=" + balance +
                '}';
    }

    /**
     * Метод equals()
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number == account.number;
    }

    /**
     * Метод hashcode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
