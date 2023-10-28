package com.denismiagkov.walletservice.domain.model;

import com.denismiagkov.walletservice.domain.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        this.account = new Account("231456739870");
    }

    @Test
    void getNumber() {
        assertEquals("231456739870", account.getNumber());
    }

    @Test
    void getBalance() {
        assertEquals(BigDecimal.valueOf(0), account.getBalance());
    }

    @Test
    void setBalance() {
        account.setBalance(BigDecimal.valueOf(1500));
        assertEquals(BigDecimal.valueOf(1500), account.getBalance());
    }


    @Test
    void testToString() {
    }

    @Test
    void testEquals_sameNumbers_true() {
        Account account1 = new Account("231456739870");
        assertTrue(account1.equals(account));
    }

    @Test
    void testEquals_differentNumbers_false() {
        Account account1 = new Account("1111111111111");
        assertFalse(account1.equals(account));
    }

    @Test
    void testHashCode_sameNumbers_true() {
        Account account1 = new Account("231456739870");
        assertEquals(account.hashCode(), account1.hashCode());
    }

    @Test
    void testHashCode_differentNumbers_false() {
        Account account1 = new Account("2222222222222");
        assertFalse(account.hashCode() == account1.hashCode());
    }
}