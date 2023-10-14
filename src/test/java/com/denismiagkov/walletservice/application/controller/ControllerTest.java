package com.denismiagkov.walletservice.application.controller;

import com.denismiagkov.walletservice.application.controller.Controller;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotUniqueTransactionIdException;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller;
    Service service;


    @BeforeEach
    void setUp() {
        service = new Service();
        controller = new Controller(service);
    }

    @Test
    void registerPlayer() {
        /**
         * Журнал операций не содержит записей.
         * */
        assertEquals(0, service.getOsi().getLog().size());
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        /**
         * В журнале аудита появилась запись с типом регистрация.
         * */
        assertEquals(OperationType.REGISTRATION, service.getOsi().getLog().get(0).getType());
        /**
         * В списке игроков появилась запись о зарегистрированном игроке.
         * */
        Player player = new Player("Petr", "Ivanov", "petr@mail.ru");
        assertTrue(service.getPsi().getAllPlayers().contains(player));
    }

    @Test
    void authorizePlayer() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        /**
         * множество учетных записей содержит логин "user1"
         * */
        assertTrue(service.getPsi().getAllEntries().containsKey("user1"));
        /**
         * журнал аудита содержит одну запись о регистрации
         * */
        assertEquals(1, service.getOsi().getLog().size());
        /**
         * авторизация по логину "user1" успешна
         * */
        assertTrue(service.authorizePlayer("user1", "123"));
        /**
         * в журнал аудита добавлена запись об авторизации
         * */
        assertEquals(2, service.getOsi().getLog().size());

    }

    @Test
    void getCurrentBalance() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        controller.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(new BigDecimal("750"), controller.getCurrentBalance("user1", "123"));
    }

    @Test
    void getTransactionsHistory() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(0, service.getTransactionsHistory("user1", "123").size());
        controller.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(1, service.getTransactionsHistory("user1", "123").size());
        controller.getCurrentBalance("user1", "123");
        assertEquals(1, service.getTransactionsHistory("user1", "123").size());
        controller.writeOffFunds("user1", "123", "2", new BigDecimal("300"));
        assertEquals(2, service.getTransactionsHistory("user1", "123").size());
    }

    @Test
    void topUpAccount() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(new BigDecimal("0"), controller.getCurrentBalance("user1", "123"));
        controller.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(new BigDecimal("750"), controller.getCurrentBalance("user1", "123"));
    }

    @Test
    void topUpAccount_incorrectID() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        controller.topUpAccount("user1", "123", "1", new BigDecimal("300"));
        assertEquals(new BigDecimal("300"), controller.getCurrentBalance("user1", "123"));
        try {
            controller.topUpAccount("user1", "123", "1", new BigDecimal("200"));
        } catch (NotUniqueTransactionIdException e) {
        } finally {
            assertEquals(new BigDecimal("300"), controller.getCurrentBalance("user1", "123"));
        }
    }

    @Test
    void writeOffFunds() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(new BigDecimal("0"), service.getCurrentBalance("user1", "123"));
        controller.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(new BigDecimal("750"), service.getCurrentBalance("user1", "123"));
        controller.writeOffFunds("user1", "123", "2", new BigDecimal("500"));
        assertEquals(new BigDecimal("250"), service.getCurrentBalance("user1", "123"));
    }

    @Test
    void writeOffFunds_incorrectID() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        controller.topUpAccount("user1", "123", "1", new BigDecimal("300"));
        assertEquals(new BigDecimal("300"), controller.getCurrentBalance("user1", "123"));
        try {
            controller.writeOffFunds("user1", "123", "1", new BigDecimal("200"));
        } catch (NotUniqueTransactionIdException e) {
        } finally {
            assertEquals(new BigDecimal("300"), controller.getCurrentBalance("user1", "123"));
        }
    }

    @Test
    void logExit() {
        controller.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(1, service.getOsi().getLog().size());
        assertEquals(OperationType.REGISTRATION, service.getOsi().getLog().get(0).getType());
        controller.logExit("user1", "123");
        assertEquals(2, service.getOsi().getLog().size());
        assertEquals(OperationType.EXIT, service.getOsi().getLog().get(1).getType());
    }
}