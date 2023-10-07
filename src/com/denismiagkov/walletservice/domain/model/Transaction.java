package com.denismiagkov.walletservice.domain.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Transaction {
    String id;
    String accountNumber;
    Timestamp time;
    TransactionType type;
    BigDecimal amount;

    public Transaction() {
    }

    public Transaction(String id, Account account, Timestamp time, TransactionType type,
                       BigDecimal amount) {
        this.id = id;
        this.accountNumber = account.getNumber();
        this.time = time;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return accountNumber;
    }

    public void setAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", accountNumber=" + accountNumber +
                ", time=" + time +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
