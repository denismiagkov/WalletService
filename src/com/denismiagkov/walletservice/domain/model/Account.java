package com.denismiagkov.walletservice.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс описывает денежный счет игрока
 * */
public class Account {
    /**
     * Номер счета
     * */
    String number;
    /**
     * Баланс счета
     * */
    BigDecimal balance;

    /**
     * Список совершенных транзакций по счету игрока
     * */
    List<Transaction> transactionInventory;

    public Account(String number) {
        this.number = number;
        this.balance = new BigDecimal(0);
        this.transactionInventory = new ArrayList<>();
    }

    /**
     * Метод возвращает номер счета
     * */
    public String getNumber() {
        return number;
    }


    /**
     * Метод возвращает баланс счета
     * */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Метод устанавливает баланс счета
     * */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Метод возвращает список транзакций по счету
     * */
    public List<Transaction> getTransactionInventory() {
        return transactionInventory;
    }

    /**
     * Метод toString()
     * */
    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", balance=" + balance +
                '}';
    }

    /**
     * Метод equals()
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number == account.number;
    }

    /**
     * Метод hashcode()
     * */
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
