package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Account;

import java.math.BigDecimal;

public interface TransactionService {
    void topUpAccount(String uniqueId, Account account, BigDecimal amount);
    void writeOffFunds(String uniqueId, Account account, BigDecimal amount);
}
