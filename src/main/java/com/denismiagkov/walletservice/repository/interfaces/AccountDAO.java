package com.denismiagkov.walletservice.repository.interfaces;

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
    BigDecimal getCurrentBalance(int playerId);

    /**
     * Метод возвращает историю транзакций по счету игрока
     *
     * @see AccountDAOImpl#getTransactionHistory(int)
     */
    public List<Transaction> getTransactionHistory(int playerId);

    /**
     * Метод сохраняет в базе данных состояние счета после совершенной транзакции
     *
     * @see AccountDAOImpl#changeBalance(int, BigDecimal)
     */
    void changeBalance(int playerId, BigDecimal balance);
}
