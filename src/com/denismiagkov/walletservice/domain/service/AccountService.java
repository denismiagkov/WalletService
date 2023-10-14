package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс объявляет методы, реализующие бизнес-логику модели счета.
 */
public interface AccountService {

    /**
     * Метод должен реализовывать создание счета
     */
    public void createAccount(Player player);

    /**
     * * Метод должен реализовывать просмотр текущего баланса
     */
    public BigDecimal getCurrentBalance(Player player);

    /**
     * Метод должен реализовывать просмотр истории транзакций
     */
    public List<Transaction> showTransactionsHistory(Player player);
}
