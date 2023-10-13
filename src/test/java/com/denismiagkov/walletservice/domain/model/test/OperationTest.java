package com.denismiagkov.walletservice.domain.model.test;

import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {
    Player player;
    Operation operation1;
    Operation operation2;
    Service service;

    @BeforeEach
    void setUp() {
        player = new Player("Ivan", "Petrov", "123@mail.ru");
        operation1 = new Operation(OperationType.REGISTRATION, new Timestamp(System.currentTimeMillis()),
                player, OperationStatus.SUCCESS);
        operation2 = new Operation(OperationType.AUTHORIZATION, new Timestamp(System.currentTimeMillis()),
                player, OperationStatus.SUCCESS);
        service = new Service();
    }

    @Test
    void getType() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(OperationType.REGISTRATION, service.getOsi().getLog().get(0).getType());
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
        assertNotEquals(operation1, operation2);
    }

    @Test
    void testHashCode() {
        assertNotEquals(operation1.hashCode(), operation2.hashCode());
    }
}