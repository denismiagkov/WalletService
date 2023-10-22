package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionDto {
    Timestamp time;
    TransactionType type;
    BigDecimal amount;
}
