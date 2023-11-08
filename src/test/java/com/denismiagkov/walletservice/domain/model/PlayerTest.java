package com.denismiagkov.walletservice.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
Player player;

    @BeforeEach
    void setUp() {
        player = new Player(1, "Ivan", "Petrov", "123@mail.ru");
    }


    @Test
    void id_after_setId() {
        player.setId(2);
        assertEquals(2, player.getId());
    }

    @Test
    void id_equals_player_getId() {
        assertEquals(1, player.getId());
    }

    @Test
    void name_equals_player_getFirstName() {
        assertEquals("Ivan", player.getFirstName());
    }

    @Test
    void lastname_equals_player_getLastName() {
        assertEquals("Petrov", player.getLastName());
    }

    @Test
    void email_equals_player_getEmail() {
        assertEquals("123@mail.ru", player.getEmail());
    }

    @Test
    void account_equals_player_getAccount() {
        Account account = new Account("12345");
        player.setAccount(account);
        assertEquals(account, player.getAccount());
    }

    @Test
    void account_equals_set_account() {
        Account account = new Account("1234523");
        player.setAccount(account);
        Account account2 = new Account("0987654");
        player.setAccount(account2);
        assertEquals(account2, player.getAccount());
    }
}