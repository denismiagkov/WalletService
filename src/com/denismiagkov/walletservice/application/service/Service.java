package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.domain.model.*;
import com.denismiagkov.walletservice.domain.model.service.OperationService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

public class Service {
    private static final Logger logger = Logger.getLogger(Service.class.getName());
    private PlayerServiceImpl psi;
    private AccountServiceImpl asi = new AccountServiceImpl();
    private TransactionServiceImpl tsi;
    private OperationServiceImpl osi;

    public Service() {
        this.psi = new PlayerServiceImpl();
        this.asi = new AccountServiceImpl();
        this.tsi = new TransactionServiceImpl();
        this.osi = new OperationServiceImpl();
    }

    public OperationServiceImpl getOsi() {
        return osi;
    }

    public void registerPlayer(String firstName, String lastName, String email, String login, String password)
            throws RuntimeException {
        Player player = psi.registerPlayer(firstName, lastName, email, login, password);
        asi.createAccount(player);
        osi.putOnLog(player, OperationType.REGISTRATION, new Timestamp(System.currentTimeMillis()),
                OperationStatus.SUCCESS);
    }

    public Player getPlayer(String login, String password) throws RuntimeException {
        return psi.authorizePlayer(login, password);
    }

    public boolean authorizePlayer(String login, String password) throws RuntimeException {
        Player player = null;
        try {
            player = psi.authorizePlayer(login, password);
            if (player != null) {
                osi.putOnLog(player, OperationType.AUTHORIZATION, new Timestamp(System.currentTimeMillis()),
                        OperationStatus.SUCCESS);
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            osi.putOnLog(player, OperationType.AUTHORIZATION, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.FAIL);
            System.out.println(e.getMessage());
        }
        return false;
    }

    public BigDecimal getCurrentBalance(String login, String password) {
        Player player = getPlayer(login, password);
        try {
            BigDecimal balance = asi.getCurrentBalance(player);
            osi.putOnLog(player, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.SUCCESS);
            return balance;
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.FAIL);
            return null;
        }
    }

    public List<Transaction> getTransactionsHistory(String login, String password) {
        Player player = getPlayer(login, password);
        try {
            List<Transaction> transactionsHistory = asi.showTransactionsHistory(player);
            osi.putOnLog(player, OperationType.TRANSACTION_HISTORY_LOOKUP,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
            return transactionsHistory;
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.TRANSACTION_HISTORY_LOOKUP,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            return null;
        }
    }

    public void topUpAccount(String login, String password, String uniqueId, BigDecimal amount)
            throws RuntimeException {
        Player player = getPlayer(login, password);
        try {
            tsi.topUpAccount(uniqueId, player.getAccount(), amount);
            osi.putOnLog(player, OperationType.CREDITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.CREDITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            throw e;
        }
    }

    public void writeOffFunds(String login, String password, String uniqueId, BigDecimal amount)
            throws RuntimeException {
        Player player = getPlayer(login, password);
        try {
            tsi.writeOffFunds(uniqueId, player.getAccount(), amount);
            osi.putOnLog(player, OperationType.DEBITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.DEBITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            throw e;
        }
    }

    public void logExit(String login, String password) {
        Player player = getPlayer(login, password);
        osi.putOnLog(player, OperationType.EXIT, new Timestamp(System.currentTimeMillis()),
                OperationStatus.SUCCESS);
        System.out.println(osi.getLog());
    }
}




