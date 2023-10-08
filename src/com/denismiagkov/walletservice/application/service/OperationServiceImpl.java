package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.service.OperationService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OperationServiceImpl implements OperationService {
    private List<Operation> log;

    public OperationServiceImpl() {
        this.log = new ArrayList<>();
    }

    public List<Operation> getLog() {
        return log;
    }

    @Override
    public void putOnLog(Player player, OperationType type, Timestamp time, OperationStatus status) {
        Operation operation = new Operation(type, time, player, status);
        log.add(operation);
    }

    public List<Operation> viewLog() {
        return getLog();
    }
}
