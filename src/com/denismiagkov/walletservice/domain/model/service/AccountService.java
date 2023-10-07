package com.denismiagkov.walletservice.domain.model.service;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public void createAccount(Player player);
    public BigDecimal getCurrentBalance(Player player);
    public List<Transaction> showTransactionsHistory(String accountNumber);
}
