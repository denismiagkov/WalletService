package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.service.AccountService;

import java.math.BigDecimal;
import java.util.*;

public class AccountServiceImpl implements AccountService {
    Set<String> accountsInventory;

    public AccountServiceImpl() {
        this.accountsInventory = new HashSet<>();
    }

    public AccountServiceImpl(HashSet<String> accountsInventory) {
        this.accountsInventory = accountsInventory;
    }

    public String getAccountNumber() {
        while (true) {
            Random n = new Random();
            String number = String.valueOf(n.nextInt(100_000_000, 999_000_000));
            if (!accountsInventory.contains(number)) {
                return number;
            }
        }
    }

    @Override
    public void createAccount(Player player) {
        Account account = new Account(getAccountNumber());
        player.setAccount(account);
        accountsInventory.add(account.getNumber());
    }

    public BigDecimal getCurrentBalance(Player player) {
        return player.getAccount().getBalance();
    }

    @Override
    public List<Transaction> showTransactionsHistory(String accountNumber) {
        return null;
    }


}
