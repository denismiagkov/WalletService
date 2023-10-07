package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.application.service.exception.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.application.service.exception.NotUniqueTransactionIdException;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.service.TransactionService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import static com.denismiagkov.walletservice.domain.model.TransactionType.CREDIT;

public class TransactionServiceImpl implements TransactionService {

    private Map<String, Transaction> transactionsInventory;

    @Override
    public void topUpAccount(String uniqueId, Account account, BigDecimal amount) {
        if (transactionsInventory.containsKey(uniqueId)) {
            throw new NotUniqueTransactionIdException(uniqueId);
        } else {
            Transaction transaction = new Transaction(uniqueId, account,
                    new Timestamp(System.currentTimeMillis()), CREDIT, amount);
            transactionsInventory.put(uniqueId, transaction);
        }
    }

    @Override
    public void writeOffFunds(String uniqueId, Account account, BigDecimal amount) {
        if (transactionsInventory.containsKey(uniqueId)) {
            throw new NotUniqueTransactionIdException(uniqueId);
        } else if (account.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsOnAccountException();
        } else {
            Transaction transaction = new Transaction(uniqueId, account,
                    new Timestamp(System.currentTimeMillis()), CREDIT, amount);
            transactionsInventory.put(uniqueId, transaction);
        }
    }
}
