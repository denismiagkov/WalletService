package com.denismiagkov.walletservice.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    Account account = new Account("12345");

    @BeforeEach
    void setUp() {
    }

    @Test
    void getId_Should_return_true() {
        account.setId(15);
        assertEquals(15, account.getId());
    }

    @Test
    void setId() {
        account.setId(1);
        assertEquals(1, account.getId());
    }

    @Test
    void getNumber() {
        assertEquals("12345", account.getNumber());
    }

    @Test
    void getBalance() {
        assertEquals(BigDecimal.valueOf(0), account.getBalance());

    }

    @Test
    void setBalance() {
        account.setBalance(new BigDecimal(1500));
        assertEquals(BigDecimal.valueOf(1500), account.getBalance());

    }

    @Test
    void setNumber() {
        account.setNumber("654321");
        assertEquals("654321", account.getNumber());
    }
}