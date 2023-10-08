package com.denismiagkov.walletservice.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    String number;
    BigDecimal balance;
    List<Transaction> transactionInventory;


    public Account() {
    }

    public Account(String number) {
        this.number = number;
        this.balance = new BigDecimal(0);
        this.transactionInventory = new ArrayList<>();
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

    public List<Transaction> getTransactionInventory() {
        return transactionInventory;
    }

    public void setTransactionInventory(List<Transaction> transactionInventory) {
        this.transactionInventory = transactionInventory;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", balance=" + balance +
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
