package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotUniqueTransactionIdException;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.domain.service.TransactionService;

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

    /**
     * Перечень уникальных идентификаторов транзакций (дебетовых и кредитных операций), совершенных в системе
     */
    Set<String> transactionIdList;


    /**
     * Констркутор класса
     * */
    public TransactionServiceImpl() {
        this.transactionIdList = new HashSet<>();
    }

    /**
     * Метод добавляет денежные средства на счет игрока (выполняет кредитную транзакцию), при условии,
     * что вызывающей стороной предоставлен уникальный идентификатор транзакции. В случае успешной операции метод
     * вносит ее идентификатор в перечень уникальных идентификаторов транзакций.
     *
     * @param uniqueId идентификатор транзакции, предоставляемый пользователем
     * @param account  денежный счет игрока, на котором выполняется транзакция
     * @param amount   сумма выполняемой операции
     * @throws NotUniqueTransactionIdException в случае, если предоставленный идентификатор транзакции не является
     *                                         уникальным
     */
    @Override
    public void topUpAccount(String uniqueId, Account account, BigDecimal amount) throws RuntimeException {
        if (this.transactionIdList.contains(uniqueId)) {
            throw new NotUniqueTransactionIdException(uniqueId);
        } else {
            Transaction transaction = new Transaction(uniqueId, account,
                    new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT, amount);
            account.setBalance(account.getBalance().add(amount));
            account.getTransactionInventory().add(transaction);
            this.transactionIdList.add(uniqueId);
        }
    }

    /**
     * Метод списывает средства с денежного счета игрока (выполняет кредитную транзакцию), при условии,
     * что на счете достаточно денежных средств и вызывающей стороной предоставлен уникальный идентификатор транзакции.
     * В случае успешной операции метод вносит ее идентификатор в перечень уникальных идентификаторов транзакций.
     *
     * @param uniqueId идентификатор транзакции, предоставляемый пользователем
     * @param account  денежный счет игрока, на котором выполняется транзакция
     * @param amount   сумма выполняемой операции
     * @throws NotUniqueTransactionIdException  в случае, если предоставленный идентификатор транзакции не является
     *                                          уникальным
     * @throws NotEnoughFundsOnAccountException в случае, если на счете игрока недостаточно денежных средств для
     *                                          совершения транзакции
     */
    @Override
    public void writeOffFunds(String uniqueId, Account account, BigDecimal amount) throws RuntimeException {
        if (transactionIdList.contains(uniqueId)) {
            throw new NotUniqueTransactionIdException(uniqueId);
        } else if (account.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsOnAccountException();
        } else {
            Transaction transaction = new Transaction(uniqueId, account,
                    new Timestamp(System.currentTimeMillis()), TransactionType.DEBIT, amount);
            account.setBalance(account.getBalance().subtract(amount));
            account.getTransactionInventory().add(transaction);
            this.transactionIdList.add(uniqueId);
        }
    }
}
