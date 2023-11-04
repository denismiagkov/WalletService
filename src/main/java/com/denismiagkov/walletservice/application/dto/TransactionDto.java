package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionDto {
    public Timestamp time;
    public TransactionType type;
    public BigDecimal amount;

    public TransactionDto() {
    }

    public Timestamp getTime() {
        return time;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "time=" + time +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}
