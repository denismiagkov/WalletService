package com.denismiagkov.walletservice.application.service.test;

import com.denismiagkov.walletservice.application.service.AccountServiceImpl;
import com.denismiagkov.walletservice.application.service.TransactionServiceImpl;
import com.denismiagkov.walletservice.application.service.exception.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.application.service.exception.NotUniqueTransactionIdException;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceImplTest {
    TransactionServiceImpl tsi;
    Player player;

    @BeforeEach
    void setUp() {
        tsi = new TransactionServiceImpl();
        player = new Player("Ivan", "Petrov", "123@mail.ru");
        AccountServiceImpl asi = new AccountServiceImpl();
        asi.createAccount(player);
    }

    @Test
    void topUpAccount_NotUniqueTransactionIdException() {
        tsi.topUpAccount("123", player.getAccount(), new BigDecimal("200"));
        assertThrows(NotUniqueTransactionIdException.class, ()-> tsi.topUpAccount("123",
                player.getAccount(), new BigDecimal("500")));
    }

    @Test
    void topUpAccount() {
        tsi.topUpAccount("123", player.getAccount(), new BigDecimal("200"));
        assertThrows(NotUniqueTransactionIdException.class, ()-> tsi.topUpAccount("123",
                player.getAccount(), new BigDecimal("500")));
    }

    @Test
    void writeOffFunds_NotUniqueTransactionIdException() {
        tsi.topUpAccount("223", player.getAccount(), new BigDecimal("500"));
        assertThrows(NotUniqueTransactionIdException.class, ()-> tsi.writeOffFunds("223",
                player.getAccount(), new BigDecimal("700")));
    }

    @Test
    void writeOff_NotEnoughFundsOnAccountException() {
        tsi.topUpAccount("223", player.getAccount(), new BigDecimal("500"));
        assertThrows(NotEnoughFundsOnAccountException.class, ()-> tsi.writeOffFunds("224",
                player.getAccount(), new BigDecimal("700")));
    }

    @Test
    void writeOff() {
        tsi.topUpAccount("223", player.getAccount(), new BigDecimal("500"));
        tsi.writeOffFunds("224", player.getAccount(), new BigDecimal("400"));
        assertEquals(new BigDecimal("100"), player.getAccount().getBalance());
    }
}