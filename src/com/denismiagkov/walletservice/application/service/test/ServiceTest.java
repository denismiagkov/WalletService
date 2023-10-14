package com.denismiagkov.walletservice.application.service.test;

import com.denismiagkov.walletservice.application.service.*;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.NotUniqueTransactionIdException;
import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.OperationServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.TransactionServiceImpl;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    Service service;
    PlayerServiceImpl psi;
    AccountServiceImpl asi;
    TransactionServiceImpl tsi;
    OperationServiceImpl osi;

    @BeforeEach
    void setUp() {
        service = new Service();
        psi = new PlayerServiceImpl();
        asi = new AccountServiceImpl();
        tsi = new TransactionServiceImpl();
        osi = new OperationServiceImpl();
    }

    @Test
    void testRegisterPlayer_correctWork() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(OperationType.REGISTRATION, service.getOsi().getLog().get(0).getType());
        // журнал аудита содержит одну запись
        Player player = new Player("Petr", "Ivanov", "petr@mail.ru");
        assertTrue(service.getPsi().getAllPlayers().contains(player));
    }

    @Test
    void testRegisterPlayer_playerAlreadyRegistered() {
        try {
            service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
            service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user2", "567");
        } catch (Exception e) {
        } finally {
            assertEquals(1, service.getPsi().getAllPlayers().size());
            // множество игроков содержит одну запись
            assertEquals(1, service.getPsi().getAllEntries().size());
            // множество учетных записей содержит одну запись
            assertTrue(service.getPsi().getAllEntries().containsKey("user1"));
            // единственный зарегистрированный логин = "user1"
        }
    }

    @Test
    void testRegisterPlayer_duplicatedLogin() {
        Player player1 = new Player("Petr", "Ivanov", "petr@mail.ru");
        Player player2 = new Player("Ivan", "Sidorov", "ivan@yandex.ru");
        try {
            service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
            service.registerPlayer("Ivan", "Sidorov", "ivan@yandex.ru", "user1", "567");
        } catch (Exception e) {
        } finally {
            assertTrue(service.getPsi().getAllPlayers().contains(player1));
            assertFalse(service.getPsi().getAllPlayers().contains(player2));
        }
    }

    /**
     * Ошибки в методе перехватываются на уровне класса PlayerServiceImpl
     */
    @Test
    void testAuthorizePlayer_correctWork() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        /**
         * множество учетных записей содержит логин "user1"
         * */
        assertTrue(service.getPsi().getAllEntries().containsKey("user1")); //множество учетных записей содержит логин "user1"
        /**
         * журнал аудита содержит одну запись о регистрации
         * */
        assertEquals(1, service.getOsi().getLog().size()); // журнал аудита содержит одну запись о регистрации
        /**
         * авторизация по логину "user1" успешна
         * */
        assertTrue(service.authorizePlayer("user1", "123"));  //авторизация по логину "user1" успешна
        /**
         * в журнал аудита добавлена запись об авторизации
         * */
        assertEquals(2, service.getOsi().getLog().size()); // в журнал аудита добавлена запись об авторизации

    }

    @Test
    void testGetPlayer_correctWork() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        Player player1 = new Player("Petr", "Ivanov", "petr@mail.ru");
        Player player2 = service.getPlayer("user1", "123");
        assertEquals(player1, player2);
    }

    @Test
    void testGetPlayer_incorrectLogin() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        Player player = null;
        try {
            player = service.getPlayer("user2", "123");
        } catch (Exception e) {
        } finally {
            assertNull(player);
        }
    }

    @Test
    void testGetPlayer_incorrectPassword() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        Player player = null;
        try {
            player = service.getPlayer("user1", "122");
        } catch (Exception e) {
        } finally {
            assertNull(player);
        }
    }

    @Test
    void getCurrentBalance() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        service.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(new BigDecimal("750"), service.getCurrentBalance("user1", "123"));
    }

    @Test
    void getTransactionsHistory_writeAndReadTransactions() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(0, service.getTransactionsHistory("user1", "123").size());
        service.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(1, service.getTransactionsHistory("user1", "123").size());
        service.getCurrentBalance("user1", "123");
        assertEquals(1, service.getTransactionsHistory("user1", "123").size());
        service.writeOffFunds("user1", "123", "2", new BigDecimal("300"));
        assertEquals(2, service.getTransactionsHistory("user1", "123").size());
    }

    @Test
    void getTransactionsHistory_checkAudit() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(1, service.getOsi().getLog().size());
        service.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(2, service.getOsi().getLog().size());
        service.getCurrentBalance("user1", "123");
        assertEquals(3, service.getOsi().getLog().size());
        service.writeOffFunds("user1", "123", "2", new BigDecimal("300"));
        assertEquals(4, service.getOsi().getLog().size());
    }

    @Test
    void topUpAccount() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(new BigDecimal("0"), service.getCurrentBalance("user1", "123"));
        service.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(new BigDecimal("750"), service.getCurrentBalance("user1", "123"));
    }

    /**
     * Ошибка неуникального id перехватывается в классе TransactionServiceImpl
     */
    @Test
    void topUpAccount_incorrectID() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        service.topUpAccount("user1", "123", "1", new BigDecimal("300"));
        assertEquals(new BigDecimal("300"), service.getCurrentBalance("user1", "123"));
        try {
            service.topUpAccount("user1", "123", "1", new BigDecimal("200"));
        } catch (NotUniqueTransactionIdException e) {
        } finally {
            assertEquals(new BigDecimal("300"), service.getCurrentBalance("user1", "123"));
        }
    }

    @Test
    void writeOffFunds() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(new BigDecimal("0"), service.getCurrentBalance("user1", "123"));
        service.topUpAccount("user1", "123", "1", new BigDecimal("750"));
        assertEquals(new BigDecimal("750"), service.getCurrentBalance("user1", "123"));
        service.writeOffFunds("user1", "123", "2", new BigDecimal("500"));
        assertEquals(new BigDecimal("250"), service.getCurrentBalance("user1", "123"));
    }

    /**
     * Ошибка недостатка денежных средств перехватывается в классе TransactionServiceImpl
     */
    @Test
    void writeOffFunds_lackOfMoney() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        service.topUpAccount("user1", "123", "1", new BigDecimal("300"));
        assertEquals(new BigDecimal("300"), service.getCurrentBalance("user1", "123"));
        try {
            service.writeOffFunds("user1", "123", "2", new BigDecimal("500"));
        } catch (NotEnoughFundsOnAccountException e) {
        } finally {
            assertEquals(new BigDecimal("300"), service.getCurrentBalance("user1", "123"));
        }
    }

    /**
     * Ошибка неуникального id перехватывается в классе TransactionServiceImpl
     */
    @Test
    void writeOffFunds_incorrectID() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        service.topUpAccount("user1", "123", "1", new BigDecimal("300"));
        assertEquals(new BigDecimal("300"), service.getCurrentBalance("user1", "123"));
        try {
            service.writeOffFunds("user1", "123", "1", new BigDecimal("200"));
        } catch (NotUniqueTransactionIdException e) {
        } finally {
            assertEquals(new BigDecimal("300"), service.getCurrentBalance("user1", "123"));
        }
    }

    @Test
    void writeOffFunds_checkAudit() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(1, service.getOsi().getLog().size());
        // в журнал аудита добавлена запись о регистрации
        service.topUpAccount("user1", "123", "1", new BigDecimal("300"));
        assertEquals(2, service.getOsi().getLog().size());
        // в журнал аудита добавлена запись о пополнении баланса
        try {
            service.writeOffFunds("user1", "123", "2", new BigDecimal("500"));
        } catch (NotEnoughFundsOnAccountException e) {
        } finally {
            assertEquals(3, service.getOsi().getLog().size());
            // в журнал аудита добавлена запись о списании средств
        }
    }

    @Test
    void logExit() {
        service.registerPlayer("Petr", "Ivanov", "petr@mail.ru", "user1", "123");
        assertEquals(1, service.getOsi().getLog().size());
        assertEquals(OperationType.REGISTRATION, service.getOsi().getLog().get(0).getType());
        service.logExit("user1", "123");
        assertEquals(2, service.getOsi().getLog().size());
        assertEquals(OperationType.EXIT, service.getOsi().getLog().get(1).getType());
    }
}