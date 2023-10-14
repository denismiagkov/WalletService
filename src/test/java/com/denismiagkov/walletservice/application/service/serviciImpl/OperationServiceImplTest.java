package com.denismiagkov.walletservice.application.service.serviciImpl;

import com.denismiagkov.walletservice.application.service.serviceImpl.OperationServiceImpl;
import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OperationServiceImplTest {
    OperationServiceImpl osi;

    @BeforeEach
    void setUp() {
        osi = new OperationServiceImpl();
    }

    @Test
    void getLog() {
        assertNotNull(osi.getLog());
        assertInstanceOf(List.class.getClass(), osi.getLog().getClass());
    }

    @Test
    void putOnLog() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        osi.putOnLog(player, OperationType.DEBITING,
                new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
        assertNotNull(osi.getLog().get(0));
    }

    @Test
    void viewLog() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        osi.putOnLog(player, OperationType.DEBITING,
                time, OperationStatus.FAIL);
        Operation operation = new Operation(OperationType.DEBITING, time, player, OperationStatus.FAIL);
        assertEquals(operation, osi.getLog().get(0));
    }
}