package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class Service {
    PlayerServiceImpl psi;
    AccountServiceImpl asi = new AccountServiceImpl();
    TransactionServiceImpl tsi;

    public Service() {
        this.psi = new PlayerServiceImpl();
        this.asi = new AccountServiceImpl();
        this.tsi = new TransactionServiceImpl();
    }

    public void registerPlayer(String firstName, String lastName, String email, String login, String password) {
        Player player = psi.registerPlayer(firstName, lastName, email, login, password);
        asi.createAccount(player);
    }

    public Player authorizePlayer(String login, String password) {
        return psi.authorizePlayer(login, password);
    }

    public BigDecimal getCurrentBalance(String login, String password) {
        Player player = authorizePlayer(login, password);
        return asi.getCurrentBalance(player);
    }

    public List<Transaction> getTransactionsHistory(String login, String password) {
        Player player = authorizePlayer(login, password);
        return asi.showTransactionsHistory(player);
    }

    public void topUpAccount(String login, String password, String uniqueId, BigDecimal amount) {
        Player player = authorizePlayer(login, password);
        tsi.topUpAccount(uniqueId, player.getAccount(), amount);
    }

    public void writeOffFunds(String login, String password, String uniqueId, BigDecimal amount) {
        Player player = authorizePlayer(login, password);
        tsi.writeOffFunds(uniqueId, player.getAccount(), amount);
    }
}




