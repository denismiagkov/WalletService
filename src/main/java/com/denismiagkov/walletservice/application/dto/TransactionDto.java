package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Schema(description = "Сущность транзакции")
public class TransactionDto {
    @Schema(description = "Время совершения транзакции")
    public Timestamp time;
    @Schema(description = "Тип транзакции", examples = {"DEBIT", "CREDIT"})
    public TransactionType type;
    @Schema(description = "Сумма транзакции")
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
