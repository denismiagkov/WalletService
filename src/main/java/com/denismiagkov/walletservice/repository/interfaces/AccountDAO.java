package com.denismiagkov.walletservice.repository.interfaces;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.repository.AccountDAOImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс объявляет методы, реализуемые репозиторием игрока
 */
public interface AccountDAO {
    /**
     * Метод сохраняет в базе данных сведения о счета игрока
     *
     * @see AccountDAOImpl#saveAccount(Player)
     */
    void saveAccount(Player player);

    /**
     * Метод возвращает текущее состояние счета игрока
     *
     * @see AccountDAOImpl#getCurrentBalance(int)
     */
    Account getCurrentBalance(int playerId);

    /**
     * Метод возвращает историю транзакций по счету игрока
     *
     * @see AccountDAOImpl#getTransactionHistory(int)
     */
    List<Transaction> getTransactionHistory(int playerId);

    /**
     * Метод рассчитывает баланс на счете игрока в случае его пополнения
     *
     * @param playerId id игрока
     * @param amount   сумма пополнения счета
     */
    void increaseBalance(int playerId, BigDecimal amount);

    /**
     * Метод рассчитывает баланс на счете игрока в случае списания средств
     *
     * @param playerId id игрока
     * @param amount   сумма пополнения счета
     */
    void decreaseBalance(int playerId, BigDecimal amount);

    /**
     * Метод возвращает id счета игрока.
     *
     * @param playerId id игрока
     * @return boolean
     */
    int getAccountId(int playerId);
}
