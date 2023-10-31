package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.domain.service.TransactionService;
import com.denismiagkov.walletservice.repository.TransactionDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Низкоуровневый сервис, реализующий методы <strong>по пополнению и списанию денежных средств со счета игрока</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    /**
     * ДАО транзакции
     */
    TransactionDAOImpl transactionDAO;

    /**
     * Констркутор класса
     */
    @Autowired
    public TransactionServiceImpl() {
        this.transactionDAO = new TransactionDAOImpl();
    }

    /**
     * Метод добавляет денежные средства на счет игрока (выполняет кредитную транзакцию)
     *
     * @param accountId идентификатор  счета, на котором выполняется транзакция
     * @param amount    сумма выполняемой операции
     */
    @Override
    public void topUpAccount(int accountId, BigDecimal amount) throws RuntimeException {
        Transaction transaction = new Transaction(accountId, new Timestamp(System.currentTimeMillis()),
                TransactionType.CREDIT, amount);
        transactionDAO.saveTransaction(transaction);
    }

    /**
     * Метод списывает средства с денежного счета игрока (выполняет кредитную транзакцию), при условии,
     * что на счете достаточно денежных средств.
     *
     * @param accountId идентификатор счета игрока, на котором выполняется транзакция
     * @param amount    сумма выполняемой операции
     */
    @Override
    public void writeOffFunds(int accountId, BigDecimal amount) throws RuntimeException {
        Transaction transaction = new Transaction(accountId,
                new Timestamp(System.currentTimeMillis()), TransactionType.DEBIT, amount);
        transactionDAO.saveTransaction(transaction);
    }
}

