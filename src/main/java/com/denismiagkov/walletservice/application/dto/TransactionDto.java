package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TransactionDto {
    private Timestamp time;
    private TransactionType type;
    private BigDecimal amount;

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
