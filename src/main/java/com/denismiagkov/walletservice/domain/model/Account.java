package com.denismiagkov.walletservice.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Класс описывает денежный счет игрока
 */
public class Account {
    /**
     * Уникальный идентификатор счета
     */
    private int id;
    /**
     * Номер счета
     */
    private String number;
    /**
     * Баланс счета
     */
    private BigDecimal balance;

    /**
     * Идентификатор игрока - владельца счета
     */
    private int playerId;

    public Account(int playerId) {
        this.playerId = playerId;
    }

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

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Метод toString()
     */
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                ", playerId=" + playerId +
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
        return number.equals(account.number);
    }

    /**
     * Метод hashcode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
