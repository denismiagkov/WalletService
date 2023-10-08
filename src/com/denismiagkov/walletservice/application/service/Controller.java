package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public void registerPlayer(String firstName, String lastName, String email, String login, String password) {
        service.registerPlayer(firstName, lastName, email, login, password);
    }

    public boolean authorizePlayer(String login, String password) {
        Player player = service.authorizePlayer(login, password);
        if(player!=null){
            return true;
        } else{
            return false;
        }
    }

    public BigDecimal getCurrentBalance(String login, String password) {
        return service.getCurrentBalance(login, password);
    }

    public String getTransactionsHistory(String login, String password) {
        return service.getTransactionsHistory(login, password).toString();
    }

    public void topUpAccount(String login, String password, String uniqueId, BigDecimal amount) {
        service.topUpAccount(login, password, uniqueId, amount);
    }

    public void writeOffFunds(String login, String password, String uniqueId, BigDecimal amount) {
        service.writeOffFunds(login, password, uniqueId, amount);
    }
}

