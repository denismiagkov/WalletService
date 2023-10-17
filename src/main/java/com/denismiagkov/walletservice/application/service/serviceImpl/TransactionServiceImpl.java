package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotUniqueTransactionIdException;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.domain.service.TransactionService;
import com.denismiagkov.walletservice.repository.TransactionDAOImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Низкоуровневый сервис, реализующий методы <strong>по пополнению и списанию денежных средств со счета игрока</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
public class TransactionServiceImpl implements TransactionService {

    TransactionDAOImpl tdi;


    /**
     * Констркутор класса
     */
    public TransactionServiceImpl() {
        this.tdi = new TransactionDAOImpl();
    }

    /**
     * Метод добавляет денежные средства на счет игрока (выполняет кредитную транзакцию), при условии,
     * что вызывающей стороной предоставлен уникальный идентификатор транзакции. В случае успешной операции метод
     * вносит ее идентификатор в перечень уникальных идентификаторов транзакций.
     *
     * @param accountId  идентификатор  счета, на котором выполняется транзакция
     * @param amount   сумма выполняемой операции
     * @throws NotUniqueTransactionIdException в случае, если предоставленный идентификатор транзакции не является
     *                                         уникальным
     */
    @Override
    public void topUpAccount(int accountId, BigDecimal amount) throws RuntimeException {
        Transaction transaction = new Transaction(accountId, new Timestamp(System.currentTimeMillis()),
                TransactionType.CREDIT, amount);
        tdi.saveTransaction(transaction);
    }

    /**
     * Метод списывает средства с денежного счета игрока (выполняет кредитную транзакцию), при условии,
     * что на счете достаточно денежных средств и вызывающей стороной предоставлен уникальный идентификатор транзакции.
     * В случае успешной операции метод вносит ее идентификатор в перечень уникальных идентификаторов транзакций.
     *
     * @param accountId  идентификатор счета игрока, на котором выполняется транзакция
     * @param amount   сумма выполняемой операции
     */
    @Override
    public void writeOffFunds(int accountId, BigDecimal amount) throws RuntimeException {
        Transaction transaction = new Transaction(accountId,
                new Timestamp(System.currentTimeMillis()), TransactionType.DEBIT, amount);
        tdi.saveTransaction(transaction);
    }
}

