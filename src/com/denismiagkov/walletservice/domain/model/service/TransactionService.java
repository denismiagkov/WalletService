package com.denismiagkov.walletservice.domain.model.service;

import java.math.BigDecimal;

public interface TransactionService {
    void topUpAccount(String id, String account, BigDecimal amount);
    void writeOffFunds(String id, String account, BigDecimal amount);
}
