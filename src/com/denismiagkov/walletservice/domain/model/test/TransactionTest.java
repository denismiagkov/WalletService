package com.denismiagkov.walletservice.domain.model.test;

import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    Transaction transaction;
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Ivan", "Petrov", "123@mail.ru");
        AccountServiceImpl asi = new AccountServiceImpl();
        asi.createAccount(player);
        transaction = new Transaction("123", player.getAccount(),
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));

    }

    @Test
    void getAccount() {
        transaction.setAccount("456");
        assertEquals("456", transaction.getAccount());
    }

    @Test
    void setAccount() {
        transaction.setAccount("789");
        assertEquals("789", transaction.getAccount());
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals_sameID() {
        Transaction transaction1 = new Transaction("123", player.getAccount(),
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertEquals(transaction1, transaction);
    }

    @Test
    void testEquals_differentID() {
        Transaction transaction1 = new Transaction("125", player.getAccount(),
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertNotEquals(transaction1, transaction);
    }

    @Test
    void testHashCode_sameId() {
        Transaction transaction1 = new Transaction("123", player.getAccount(),
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertTrue(transaction.hashCode() == transaction1.hashCode());
    }

    @Test
    void testHashCode_differentId() {
        Transaction transaction1 = new Transaction("223", player.getAccount(),
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertFalse(transaction.hashCode() == transaction1.hashCode());
    }
}