package com.denismiagkov.walletservice.domain.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс описывает транзакцию - кредитную (добавление денежных средств)
 * или дебетовую (списание денежных средств) операцию по счету игрока
 */
public class Transaction {
    /**
     * Уникальный идентификатор транзакции
     */
    int id;
    /**
     * Номер счета, на котором выполняется транзакция
     */
    int accountId;
    /**
     * Дата и время выполнения транзакции
     */
    Timestamp time;
    /**
     * Тип транзакции - дебетовая или кредитная
     *
     * @see TransactionType
     */
    TransactionType type;
    /**
     * Сумма транзакции
     */
    BigDecimal amount;


    public Transaction() {
    }

    /**
     * Конструктор класса
     */
    public Transaction(int accountId, Timestamp time, TransactionType type,
                       BigDecimal amount) {
        this.accountId = accountId;
        this.time = time;
        this.type = type;
        this.amount = amount;
    }

    public Transaction(int id, int accountId, Timestamp time, TransactionType type, BigDecimal amount) {
        this(accountId, time, type, amount);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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


    /**
     * Метод toString()
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id +
                ", accountNumber=" + accountId +
                ", time=" + time +
                ", type=" + type +
                ", amount=" + amount +
                '}' + "\n";
    }

    /**
     * Метод equals()
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Метод hashcode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
