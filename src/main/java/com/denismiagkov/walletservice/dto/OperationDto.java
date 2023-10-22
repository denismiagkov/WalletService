package com.denismiagkov.walletservice.dto;

import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;

import java.sql.Timestamp;

public class OperationDto {
    String name;
    String surname;
    Timestamp time;
    OperationType type;
    OperationStatus status;
}
