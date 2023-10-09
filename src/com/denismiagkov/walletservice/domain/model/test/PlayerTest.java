package com.denismiagkov.walletservice.domain.model.test;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testGetFirstName() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        assertEquals("Ivan", player.getFirstName());
    }

    @Test
    void testGetAccount() {
        Account account = new Account("12345");
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        player.setAccount(account);
        assertEquals(account, player.getAccount());
    }

    @Test
    void setAccount() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        Account account = new Account("1234523");
        player.setAccount(account);
        Account account2 = new Account("0987654");
        player.setAccount(account2);
        assertEquals(account2, player.getAccount());
    }

    @Test
    void testToString() {
        Player player = new Player("Sidor", "Ivanov", "2341@mail.ru");
        assertEquals("Player{firstName='Sidor', lastName='Ivanov', email='2341@mail.ru'}",
                player.toString());
    }

    @Test
    void testEquals_withSameFields_true() {
        Player player = new Player("Sidor", "Ivanov", "2341@mail.ru");
        Player player1 = new Player("Sidor", "Ivanov", "2341@mail.ru");
        assertEquals("true", String.valueOf(player.equals(player1)));
    }

    @Test
    void testEquals_withDifferentMail_false() {
        Player player = new Player("Sidor", "Ivanov", "2341@mail.ru");
        Player player1 = new Player("Sidor", "Ivanov", "9999@mail.ru");
        assertEquals("false", String.valueOf(player.equals(player1)));
    }

    @Test
    void testHashCode_withSameFields_true() {
        Player player = new Player("Sidor", "Ivanov", "2341@mail.ru");
        Player player1 = new Player("Sidor", "Ivanov", "2341@mail.ru");
        assertTrue(player.hashCode() == player1.hashCode());
    }

    @Test
    void testHashCode_withDifferentMail_false() {
        Player player = new Player("Sidor", "Ivanov", "2341@mail.ru");
        Player player1 = new Player("Sidor", "Ivanov", "9999@mail.ru");
        assertFalse(player.hashCode() == player1.hashCode());
    }
}