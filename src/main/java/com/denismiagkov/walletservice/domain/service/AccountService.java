package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.domain.model.Account;
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
    void createAccount(Player player);

    /**
     * * Метод должен реализовывать просмотр текущего баланса
     */
    Account getCurrentBalance(int playerId);

    /**
     * Метод должен реализовывать просмотр истории транзакций
     */
    List<Transaction> getTransactionHistory(int playerId);

    /**
     * Метод корректирует баланс на счете игрока после пополнения счета
     *
     * @param playerId id игрока
     * @param amount   денежная сумма
     */
    void increaseBalance(int playerId, BigDecimal amount);

    /**
     * Метод корректирует баланс на счете игрока после списания средств
     *
     * @param playerId id игрока
     * @param amount   денежная сумма
     */
    void decreaseBalance(int playerId, BigDecimal amount);

    /**
     * Метод рассчитывает, достаточно ли денежных средств на счете игрока для их списания.
     *
     * @param playerId id игрока
     * @param amount   сумма списания
     * @return boolean
     * @throws NotEnoughFundsOnAccountException в случае, если на счете игрока недостаточно денежных средств для
     *                                          совершения транзакции
     */
    boolean areFundsEnough(int playerId, BigDecimal amount);

    /**
     * Метод возвращает id денежного счета по id игрока
     *
     * @param playerId id игрока
     * @return id счета
     */
    int getAccountId(int playerId);
}
