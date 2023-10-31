package com.denismiagkov.walletservice.repository.interfaces;

import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.repository.TransactionDAOImpl;

/**
 * Интерфейс объявляет методы, реализуемые репозиторием транзакции
 */
public interface TransactionDAO {

    /**
     * Метод сохраняет в базе данных сведения о совершенной транзакции
     *
     * @see TransactionDAOImpl#saveTransaction(Transaction)
     */
    void saveTransaction(Transaction transaction);
}
