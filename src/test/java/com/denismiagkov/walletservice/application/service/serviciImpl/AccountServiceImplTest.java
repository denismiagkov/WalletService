package com.denismiagkov.walletservice.application.service.serviciImpl;

import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.TransactionServiceImpl;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    AccountServiceImpl asi;
    @BeforeEach
    void setUp() {
        asi = new AccountServiceImpl();
    }

    @Test
    void getAccountNumber() {
        String number = asi.getAccountNumber();
        assertNotNull(number);
    }

    @Test
    void createAccount() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        asi.createAccount(player);
        assertEquals(new BigDecimal("0"), player.getAccount().getBalance());
    }

    @Test
    void getCurrentBalance() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        asi.createAccount(player);
        assertEquals("0", String.valueOf(asi.getCurrentBalance(player)));
    }

    @Test
    void testShowTransactionsHistory() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        AccountServiceImpl asi = new AccountServiceImpl();
        asi.createAccount(player);
        TransactionServiceImpl tsi = new TransactionServiceImpl();
        tsi.topUpAccount("223", player.getAccount(), new BigDecimal("500"));
        tsi.writeOffFunds("224", player.getAccount(), new BigDecimal("400"));
        List<Transaction> transactionList = asi.showTransactionsHistory(player);
        assertEquals(2, transactionList.size());
    }
}