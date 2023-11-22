package com.denismiagkov.walletservice.domain.model;

//import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(123, 5,
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
    }

    @Test
    void setId() {
    }

    @Test
    void getAccountId() {
        assertEquals(5, transaction.getAccountId());
    }

    @Test
    void getTime() {
    }

    @Test
    void getType() {
        assertEquals(TransactionType.CREDIT, transaction.getType());
    }

    @Test
    void getAmount() {
        assertEquals(BigDecimal.valueOf(500), transaction.getAmount());
    }

    @Test
    void testEquals_sameID() {
        Transaction transaction1 = new Transaction(123, 5,
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertEquals(transaction1, transaction);
    }

    @Test
    void testEquals_differentID() {
        Transaction transaction1 = new Transaction(125, 5,
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertNotEquals(transaction1, transaction);
    }

    @Test
    void testHashCode_sameId() {
        Transaction transaction1 = new Transaction(123, 5,
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertTrue(transaction.hashCode() == transaction1.hashCode());
    }

    @Test
    void testHashCode_differentId() {
        Transaction transaction1 = new Transaction(223, 5,
                new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT,
                new BigDecimal("500"));
        assertFalse(transaction.hashCode() == transaction1.hashCode());
    }
}