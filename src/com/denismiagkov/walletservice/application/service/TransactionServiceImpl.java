package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.application.service.exception.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.application.service.exception.NotUniqueTransactionIdException;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.service.TransactionService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static com.denismiagkov.walletservice.domain.model.TransactionType.CREDIT;

public class TransactionServiceImpl implements TransactionService {

    Set<String> transactionIdList;

    public TransactionServiceImpl() {
        this.transactionIdList = new HashSet<>();
    }

    public TransactionServiceImpl(Set<String> transactionIdList) {
        this.transactionIdList = transactionIdList;
    }

    @Override
    public void topUpAccount(String uniqueId, Account account, BigDecimal amount) {
        if (this.transactionIdList.contains(uniqueId)) {
            throw new NotUniqueTransactionIdException(uniqueId);
        } else {
            Transaction transaction = new Transaction(uniqueId, account,
                    new Timestamp(System.currentTimeMillis()), CREDIT, amount);
            account.setBalance(account.getBalance().add(amount));
            account.getTransactionInventory().add(transaction);
            this.transactionIdList.add(uniqueId);
        }
    }

    @Override
    public void writeOffFunds(String uniqueId, Account account, BigDecimal amount) {
        if (transactionIdList.contains(uniqueId)) {
            throw new NotUniqueTransactionIdException(uniqueId);
        } else if (account.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsOnAccountException();
        } else {
            Transaction transaction = new Transaction(uniqueId, account,
                    new Timestamp(System.currentTimeMillis()), CREDIT, amount);
            account.setBalance(account.getBalance().subtract(amount));
            account.getTransactionInventory().add(transaction);
            this.transactionIdList.add(uniqueId);
        }
    }
}
