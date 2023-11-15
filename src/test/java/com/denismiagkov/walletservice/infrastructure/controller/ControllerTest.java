package com.denismiagkov.walletservice.infrastructure.controller;

import com.denismiagkov.walletservice.application.dto.*;
import com.denismiagkov.walletservice.application.service.MainService;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.LoginIsNotUniqueException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.PlayerAlreadyExistsException;
import com.denismiagkov.walletservice.domain.model.*;
import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.*;
import com.denismiagkov.walletservice.infrastructure.login_service.AuthException;
import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @Mock
    MainService mainService;
    @Mock
    AuthService authService;
    @InjectMocks
    Controller controller;
    @InjectMocks
    InfoMessage message;

    @Test
    @DisplayName("POST /api/register returns HTTP-response 200 CREATED")
    void registerPlayer_PlayerInfoIsValid_ReturnsValidResponse() {
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
                new Player(10, "Ivan", "Petrov", "123@mail.ru"),
                new Entry(10, "login10", "password10")
        );
        var responseEntity = this.controller.registerPlayer(playerDto);
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode())
        );
    }

    @Test
    @DisplayName("POST /api/register returns HTTP-response 400 BAD_REQUEST")
    void registerPlayer_PlayerNameWithNumbers_ThrowsException() {
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
                new Player(10, "111", "Petrov", "123@mail.ru"),
                new Entry(10, "login10", "password10")
        );
        assertThrows(IncorrectNameException.class, () -> controller.registerPlayer(playerDto));
    }

    @Test
    @DisplayName("POST /api/register returns HTTP-response 400 BAD_REQUEST")
    void registerPlayer_PlayerSurNameWithNumbers_ThrowsException() {
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
                new Player(10, "Ivan", "Petrov2", "123@mail.ru"),
                new Entry(10, "login10", "password10")
        );
        assertThrows(IncorrectSurnameException.class, () -> controller.registerPlayer(playerDto));
    }

    @Test
    @DisplayName("POST /api/register returns HTTP-response 400 BAD_REQUEST")
    void registerPlayer_InvalidPlayerEmail_ThrowsException() {
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
                new Player(10, "Ivan", "Petrov", "123@mailru"),
                new Entry(10, "login10", "password10")
        );
        assertThrows(IncorrectEmailException.class, () -> controller.registerPlayer(playerDto));
    }

    @Test
    @DisplayName("POST /api/register returns HTTP-response 400 BAD_REQUEST")
    void registerPlayer_LoginExists_ThrowsException() {
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
                new Player(10, "Ivan", "Petrov", "123@mail.ru"),
                new Entry(10, "login10", "password10")
        );
        doThrow(LoginIsNotUniqueException.class).when(mainService).registerPlayer(playerDto);
        assertThrows(LoginIsNotUniqueException.class, () -> controller.registerPlayer(playerDto));
    }

    @Test
    @DisplayName("POST /api/register returns HTTP-response 400 BAD_REQUEST")
    void registerPlayer_PlayerExists_ThrowsException() {
        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
                new Player(10, "Ivan", "Petrov", "123@mail.ru"),
                new Entry(10, "login10", "password10")
        );
        doThrow(PlayerAlreadyExistsException.class).when(mainService).registerPlayer(playerDto);
        assertThrows(PlayerAlreadyExistsException.class, () -> controller.registerPlayer(playerDto));
    }

    @Test
    void getTransactionsHistory_ReturnsValidResponseDto() {
        when(authService.validateAccessToken("header")).thenReturn("login1");
        var transactions = List.of(
                TransactionMapper.INSTANCE.toTransactionDto(new Transaction(1,
                        new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT, BigDecimal.valueOf(1200))),
                TransactionMapper.INSTANCE.toTransactionDto(new Transaction(1,
                        new Timestamp(System.currentTimeMillis()), TransactionType.DEBIT, BigDecimal.valueOf(800))),
                TransactionMapper.INSTANCE.toTransactionDto(new Transaction(1,
                        new Timestamp(System.currentTimeMillis()), TransactionType.DEBIT, BigDecimal.valueOf(30)))
        );
        when(mainService.getTransactionHistory("login1")).thenReturn(transactions);
        var responseEntity = this.controller.getTransactionsHistory("header");
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertEquals(transactions, responseEntity.getBody())
        );
    }

    @Test
    void topUpAccount_IncorrectToken_Throws_Exception() {
        String header = "123";
        AmountWrapper wrapper = new AmountWrapper();
        wrapper.setAmount("100");
        doThrow(AuthException.class).when(authService).validateAccessToken(header);
        assertThrows(AuthException.class, () -> controller.topUpAccount(header, wrapper));
    }

    @Test
    void topUpAccount_Incorrect_Transaction_Amount_Throws_Exception() {
        String header = "123";
        AmountWrapper wrapper = new AmountWrapper();
        wrapper.setAmount("-100");
        assertThrows(IncorrectInputNumberException.class, () -> controller.topUpAccount(header, wrapper));
    }

    @Test
    void WriteOffFunds_IncorrectToken_Throws_Exception() {
        String header = "123";
        AmountWrapper wrapper = new AmountWrapper();
        wrapper.setAmount("100");
        doThrow(AuthException.class).when(authService).validateAccessToken(header);
        assertThrows(AuthException.class, () -> controller.writeOffFunds(header, wrapper));
    }

    @Test
    void writeOffFunds_Incorrect_Transaction_Amount_Throws_Exception() {
        String header = "123";
        AmountWrapper wrapper = new AmountWrapper();
        wrapper.setAmount("-100");
        assertThrows(IncorrectInputNumberException.class, () -> controller.topUpAccount(header, wrapper));
    }
}