package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.service.OperationService;

import java.math.BigDecimal;
import java.util.List;

public class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public void registerPlayer(String firstName, String lastName, String email, String login, String password) {
        try {
            service.registerPlayer(firstName, lastName, email, login, password);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean authorizePlayer(String login, String password) {
        return service.authorizePlayer(login, password);
    }

    public BigDecimal getCurrentBalance(String login, String password) {
        return service.getCurrentBalance(login, password);
    }

    public String getTransactionsHistory(String login, String password) {
        return service.getTransactionsHistory(login, password).toString();
    }

    public boolean topUpAccount(String login, String password, String uniqueId, BigDecimal amount) {
        try {
            service.topUpAccount(login, password, uniqueId, amount);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean writeOffFunds(String login, String password, String uniqueId, BigDecimal amount) {
        try {
            service.writeOffFunds(login, password, uniqueId, amount);
            return true;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void logExit(String login, String password){
        service.logExit(login, password);
    }

}

