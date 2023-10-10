package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;

import java.sql.Timestamp;
import java.util.List;

public interface OperationService {
    void putOnLog(Player player, OperationType type, Timestamp time, OperationStatus status);
    List<Operation> viewLog();

}
