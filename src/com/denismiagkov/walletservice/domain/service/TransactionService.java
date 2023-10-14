package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Account;

import java.math.BigDecimal;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели транзакции.
 */
public interface TransactionService {
    /**
     * Метод должен реализовывать пополнеие счета
     */
    void topUpAccount(String uniqueId, Account account, BigDecimal amount);

    /**
     * Метод должен реализовывать списание денежных средств
     */
    void writeOffFunds(String uniqueId, Account account, BigDecimal amount);
}
