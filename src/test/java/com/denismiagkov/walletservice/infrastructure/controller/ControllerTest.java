package com.denismiagkov.walletservice.infrastructure.controller;
//
//import com.denismiagkov.walletservice.application.dto.AccountDto;
//import com.denismiagkov.walletservice.application.dto.PlayerDto;
//import com.denismiagkov.walletservice.application.dto.PlayerMapper;
//import com.denismiagkov.walletservice.application.dto.TransactionMapper;
//import com.denismiagkov.walletservice.application.service.MainService;
//import com.denismiagkov.walletservice.domain.model.Entry;
//import com.denismiagkov.walletservice.domain.model.Player;
//import com.denismiagkov.walletservice.domain.model.Transaction;
//import com.denismiagkov.walletservice.domain.model.TransactionType;
//import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.IncorrectInputNumberException;
//import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.InfoMessage;
//import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class ControllerTest {
//    @Mock
//    MainService mainService;
//    @Mock
//    AuthService authService;
//    @InjectMocks
//    Controller controller;
//    @InjectMocks
//    InfoMessage message;
//
//
//    @BeforeEach
//    void setUp() {
//        //    controller = new Controller(mainService, authService);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    @DisplayName("POST /api/register returns HTTP-response 200 CREATED")
//    void registerPlayer_PlayerInfoIsValid_ReturnsValidResponse() {
//        //given
//        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
//                new Player(10, "Ivan", "Petrov", "123@mail.ru"),
//                new Entry(10, "login10", "password10")
//        );
//        //when
//        var responseEntity = this.controller.registerPlayer(playerDto);
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType())
//        );
//    }
//
//    @Test
//    @DisplayName("POST /api/register returns HTTP-response 400 BAD_REQUEST")
//    void registerPlayer_PlayerNameWithNumbers_ReturnsNoteOfMistake() {
//        //given
//        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
//                new Player(10, "111", "Petrov", "123@mail.ru"),
//                new Entry(10, "login10", "password10")
//        );
//        //when
//        var responseEntity = this.controller.registerPlayer(playerDto);
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType())
//        );
//    }
//
//    @Test
//    void authorizePlayer() {
////        //given
////        JwtRequest request = new JwtRequest();
////        request.setLogin("login1");
////        request.setPassword("password1");
////        JwtResponse response = new JwtResponse("123", "456");
////        Mockito.when(this.controller.authorizePlayer(request)).thenReturn(response);
////        );
////        //when
////        var responseEntity = this.controller.registerPlayer(playerDto);
////        //then
////        assertAll(
////                () -> assertNotNull(responseEntity),
////                () -> assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode()),
////                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType())
////        );
//    }
//
//    @Test
//    void getCurrentBalance_ReturnsValidResponseDto() {
//        //given
//        AccountDto accountDto = new AccountDto();
//        //when
//
//        //then
//
//    }
//
//    @Test
//    void getTransactionsHistory_ReturnsValidResponseDto() {
//        //given
//        Mockito.when(authService.validateAccessToken("header")).thenReturn("login1");
//        var transactions = List.of(
//                TransactionMapper.INSTANCE.toTransactionDto(new Transaction(1,
//                        new Timestamp(System.currentTimeMillis()), TransactionType.CREDIT, BigDecimal.valueOf(1200))),
//                TransactionMapper.INSTANCE.toTransactionDto(new Transaction(1,
//                        new Timestamp(System.currentTimeMillis()), TransactionType.DEBIT, BigDecimal.valueOf(800))),
//                TransactionMapper.INSTANCE.toTransactionDto(new Transaction(1,
//                        new Timestamp(System.currentTimeMillis()), TransactionType.DEBIT, BigDecimal.valueOf(30)))
//        );
//        Mockito.when(mainService.getTransactionHistory("login1")).thenReturn(transactions);
//        //when
//        var responseEntity = this.controller.getTransactionsHistory("header");
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType()),
//                () -> assertEquals(transactions, responseEntity.getBody())
//        );
//
//    }
//
//    @Test
//    void topUpAccount() {
//        //given
//        String header = "123";
//        AmountWrapper wrapper = new AmountWrapper();
//        Mockito.when(this.authService.validateAccessToken(header)).thenReturn("login1");
//        Mockito.when(wrapper.getAmount()).thenReturn(BigDecimal.valueOf(500));
//        // Mockito.when(this.mainService.topUpAccount("login1", BigDecimal.valueOf(500))).
//        PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
//                new Player(10, "Ivan", "Petrov", "123@mail.ru"),
//                new Entry(10, "login10", "password10")
//        );
//        //when
//        var responseEntity = this.controller.registerPlayer(playerDto);
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType())
//        );
//    }
//
//    @Test
//    void topUpAccount_IncorrectToken_Catch_Exception() {
//        //given
//        String header = "123";
//        AmountWrapper wrapper = new AmountWrapper();
//        //when
//        var responseEntity = this.controller.topUpAccount(header, wrapper);
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType())
//        );
//    }
//
//    @Test
//    void topUpAccount_Incorrect_Transaction_Amount_Catch_Exception() {
//        //given
//        String header = "123";
//        AmountWrapper wrapper = new AmountWrapper();
//        wrapper.setAmount("-100");
//        IncorrectInputNumberException exception = new IncorrectInputNumberException();
//        //when
//        var responseEntity = this.controller.topUpAccount(header, wrapper);
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType()),
//                ()-> assertEquals(exception.getMessage(), responseEntity.getBody().getInfo())
//        );
//    }
//
//    @Test
//    void writeOffFunds_IncorrectToken_Catch_Exception() {
//        //given
//        String header = "spring";
//        AmountWrapper wrapper = new AmountWrapper();
//        //when
//        var responseEntity = this.controller.writeOffFunds(header, wrapper);
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType())
//        );
//    }
//
//    @Test
//    void writeOffFunds_Incorrect_Transaction_Amount_Catch_Exception() {
//        //given
//        String header = "123";
//        AmountWrapper wrapper = new AmountWrapper();
//        wrapper.setAmount("-100");
//        IncorrectInputNumberException exception = new IncorrectInputNumberException();
//        //when
//        var responseEntity = this.controller.topUpAccount(header, wrapper);
//        //then
//        assertAll(
//                () -> assertNotNull(responseEntity),
//                () -> assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode()),
//                () -> assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType()),
//                ()-> assertEquals(exception.getMessage(), responseEntity.getBody().getInfo())
//        );
//    }
//}