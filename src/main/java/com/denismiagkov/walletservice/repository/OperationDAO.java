package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OperationDAO {
    public void saveOperation(Operation operation);
    public List<String> getLog();
}
