package com.denismiagkov.walletservice.repository.interfaces;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.repository.TransactionDAOImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

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
