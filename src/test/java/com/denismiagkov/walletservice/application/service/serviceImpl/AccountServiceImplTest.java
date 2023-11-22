package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.repository.AccountDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {
    AccountServiceImpl accountService;
    AccountDAOImpl accountDAO;

    @BeforeEach
    void setUp() {
        accountDAO = Mockito.mock(AccountDAOImpl.class);
        accountService = new AccountServiceImpl(accountDAO);
    }

    @Test
    void getAccountNumber() {
    }

    @Test
    void createAccount() {
    }

    @Test
    void getCurrentBalance() {
        Account account = new Account(1);
        account.setNumber("12345");
        account.setBalance(BigDecimal.valueOf(5000));
        doReturn(account).when(accountDAO).getCurrentBalance(Mockito.anyInt());
        assertThat(account).isEqualTo(accountService.getCurrentBalance(1));
    }

    @Test
    void getTransactionHistory() {
        Transaction transaction1 = new Transaction(1, new Timestamp(System.currentTimeMillis()),
                TransactionType.CREDIT, new BigDecimal(540));
        Transaction transaction2 = new Transaction(1, new Timestamp(System.currentTimeMillis()),
                TransactionType.DEBIT, new BigDecimal(120));
        List<Transaction> transactions = List.of(transaction1, transaction2);
        when(accountDAO.getTransactionHistory(Mockito.anyInt())).thenReturn(transactions);
        assertThat(transactions).isEqualTo(accountService.getTransactionHistory(1));
    }

    @Test
    void increaseBalance() {
    }

    @Test
    void decreaseBalance() {
    }

    @Test
    void areFundsEnough_Throws_Exception_When_Not_Enough_Money() {
        Account account = new Account("12345");
        account.setBalance(BigDecimal.valueOf(100));
        doReturn(account).when(accountDAO).getCurrentBalance(1);
        assertAll(
                () -> {
                    var exception = assertThrows(NotEnoughFundsOnAccountException.class,
                            () -> accountService.areFundsEnough(1, BigDecimal.valueOf(200)));
                    assertThat(exception.getMessage()).isEqualTo(
                            "Недостаточно денежных средств на счете для совершения транзакции!");
                });
    }

    @Test
    void getAccountId_Should_Return_Correct_Id() {
        Account account = new Account(5);
        account.setId(7);
        doReturn(account.getId()).when(accountDAO).getAccountId(5);
        assertThat(7).isEqualTo(accountService.getAccountId(5));
    }
}