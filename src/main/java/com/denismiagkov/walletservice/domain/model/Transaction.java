package com.denismiagkov.walletservice.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс описывает транзакцию - кредитную (добавление денежных средств)
 * или дебетовую (списание денежных средств) операцию по счету игрока
 */
@Data
public class Transaction {
    /**
     * Уникальный идентификатор транзакции
     */
    private int id;
    /**
     * Номер счета, на котором выполняется транзакция
     */
    private final int accountId;
    /**
     * Дата и время выполнения транзакции
     */
    private final Timestamp time;
    /**
     * Тип транзакции - дебетовая или кредитная
     *
     * @see TransactionType
     */
    private final TransactionType type;
    /**
     * Сумма транзакции
     */
    private BigDecimal amount;

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
}
