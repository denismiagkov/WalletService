package com.denismiagkov.walletservice.domain.model.service;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Transaction;

import java.util.List;

public interface AccountService {
    Account showAccountDetails(String accountNumber);
    List<Transaction> showTransactionsHistory(String accountNumber);
}
