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
    public Timestamp time;
    public TransactionType type;
    public BigDecimal amount;

    public TransactionDto() {
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
        return "TransactionDto{" +
                "time=" + time +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}
