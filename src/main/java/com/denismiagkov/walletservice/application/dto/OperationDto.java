package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.auditstarter.operation.OperationStatus;
import com.denismiagkov.auditstarter.operation.OperationType;

import java.sql.Timestamp;

public class OperationDto {

    private Timestamp time;
    private OperationType type;
    private int playerId;
    private OperationStatus status;

    public OperationDto() {
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
