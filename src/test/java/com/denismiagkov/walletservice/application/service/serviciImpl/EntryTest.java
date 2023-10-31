package com.denismiagkov.walletservice.application.service.serviciImpl;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {
    Entry entry;
    Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Ivan", "Petrov", "123@mail.ru");
        entry = new Entry(player.getId(), "user" , "password");
    }

    @Test
    void testEquals_true() {
        Player player1 = new Player("Petr", "Ivanov", "456@mail.ru");
        Entry entry1 = new Entry(player1.getId(), "user" , "password");
        assertEquals(entry, entry1);
    }

    @Test
    void testEquals_false() {
        Player player1 = new Player("Petr", "Ivanov", "456@mail.ru");
        Entry entry1 = new Entry(player1.getId(), "user1" , "password");
        assertNotEquals(entry, entry1);
    }

    @Test
    void testHashCode() {
        Player player1 = new Player("Petr", "Ivanov", "456@mail.ru");
        Entry entry1 = new Entry(player1.getId(), "user1" , "password");
        assertNotEquals(entry.hashCode(), entry1.hashCode());
    }
}