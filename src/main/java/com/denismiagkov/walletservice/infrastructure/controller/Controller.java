package com.denismiagkov.walletservice.infrastructure.controller;

import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.dto.TransactionDto;
import com.denismiagkov.walletservice.application.service.MainService;
import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.DataValidator;
import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.InfoMessage;
import com.denismiagkov.walletservice.infrastructure.login_service.AuthService;
import com.denismiagkov.walletservice.infrastructure.login_service.JwtRequest;
import com.denismiagkov.walletservice.infrastructure.login_service.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс обрабатывает запросы, полученные от пользователя и управляет взаимодействием между внешним
 * и внутренними слоями приложения
 */
@RestController
@RequestMapping("/api")
@Tag(name = "REST-контроллер")
public class Controller {
    /**
     * Сервис приложения
     */
    private final MainService mainService;
    /**
     * Сервис аутентификации
     */
    private final AuthService authService;
    /**
     * Информационное сообщение для пользователя о статусе исполнения запроса
     */
    private InfoMessage message;


    /**
     * Конструктор класса
     */
    @Autowired
    public Controller(MainService mainService, AuthService authService) {
        this.mainService = mainService;
        this.authService = authService;
        this.message = new InfoMessage();
    }

    /**
     * Метод вызывает в сервисе метод регистрации нового игрока. Возвращает статус исполнения запроса
     *
     * @param playerDto ДТО нового игрока
     * @return статус исполнения запроса
     */
    @PostMapping("/registration")
    @Operation(summary = "Регистрация игрока", description = "Позволяет зарегистрировать нового игрока")
    public ResponseEntity<InfoMessage> registerPlayer(@RequestBody PlayerDto playerDto) throws RuntimeException {
        DataValidator.checkRegistrationForm(playerDto);
        mainService.registerPlayer(playerDto);
        message.setInfo("Игрок " + playerDto.getName() + " " + playerDto.getSurname() + " зарегистрирован");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * Метод реализует аутентентификацию пользователя и сообщает ему о результате исполнения запроса
     *
     * @param authRequest запрос на авторизацию игрока, включающий его логин и пароль
     * @return токен
     */
    @PostMapping("/authentication")
    @Operation(summary = "Аутентификация игрока", description = "Позволяет игроку пройти процедуру аутентификации")
    public ResponseEntity<?> authorizePlayer(@RequestBody JwtRequest authRequest) throws RuntimeException {
        JwtResponse authResponse = authService.login(authRequest);
        message.setInfo("Аутентификация пользователя выполнена успешно");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }


    /**
     * Метод передает в сервис запрос о текущем состоянии баланса игрока и возвращает
     * результат обработки запроса пользователю.
     *
     * @param header Header "Authorization" HttpServletRequest, содержащий токен игрока
     * @return текущий баланс на счет игрока
     */
    @PostMapping("/players/balance")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Просмотр баланса", description = "Позволяет узнать текущий баланс на счете игрока")
    public ResponseEntity<AccountDto> getCurrentBalance(@RequestHeader("Authorization") String header) {
        String login = authService.validateAccessToken(header);
        AccountDto accountDto = mainService.getCurrentBalance(login);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountDto);
    }

    /**
     * Метод вызывает в сервисе историю дебетовых и кредитных операций по счету игрока
     * и возвращает пользователю результат обработки запроса.
     *
     * @param header Header "Authorization" HttpServletRequest, содержащий токен игрока
     * @return история транзакций на счете игрока
     */
    @PostMapping("/players/transactions")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "История транзакций", description = "Позволяет просмотреть историю транзакций на счете игрока")
    public ResponseEntity<List<TransactionDto>> getTransactionsHistory(@RequestHeader("Authorization") String header) {
        String login = authService.validateAccessToken(header);
        List<TransactionDto> transactionDtoList = mainService.getTransactionHistory(login);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionDtoList);
    }

    /**
     * Метод вызывает в сервисе метод по пополнению денежного счета игрока
     * и возвращает пользователю результат обработки запроса
     *
     * @param header  Header "Authorization" HttpServletRequest, содержащий токен игрока
     * @param wrapper класс-обертка для получения значения типа BigDecimal из http-запроса
     * @return статус исполнения запроса
     */
    @PostMapping("/players/depositing")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Пополнение счета", description = "Позволяет пополнить счет игрока")
    public ResponseEntity<InfoMessage> topUpAccount(@RequestHeader("Authorization") String header,
                                                    @RequestBody AmountWrapper wrapper) {
        BigDecimal amount = wrapper.getAmount();
        String login = authService.validateAccessToken(header);
        mainService.topUpAccount(login, amount);
        message.setInfo("Ваш баланс пополнен на сумму " + amount + " " + " денежных единиц");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Метод вызывает в сервисе метод по списанию денежных средств со счета игрока
     * и возвращает пользователю результат обработки запроса.
     *
     * @param header  Header "Authorization" HttpServletRequest, содержащий токен игрока
     * @param wrapper класс-обертка для получения значения типа BigDecimal из http-запроса
     * @return статус исполнения запроса
     */
    @PostMapping("/players/withdrawal")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Списание со счета", description = "Позволяет списать денежные средства со счета игрока")
    public ResponseEntity<InfoMessage> writeOffFunds(@RequestHeader("Authorization") String header,
                                                     @RequestBody AmountWrapper wrapper) throws RuntimeException {
        BigDecimal amount = wrapper.getAmount();
        String login = authService.validateAccessToken(header);
        mainService.writeOffFunds(login, amount);
        message.setInfo("С вашего счета списана сумма " + amount + " " + " денежных единиц");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}