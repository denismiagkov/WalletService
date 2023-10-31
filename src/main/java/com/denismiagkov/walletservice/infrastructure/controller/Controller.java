package com.denismiagkov.walletservice.infrastructure.controller;

import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.dto.EntryDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.dto.TransactionDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.infrastructure.in.DataValidator;
import com.denismiagkov.walletservice.infrastructure.in.exceptions.IncorrectNameException;
import com.denismiagkov.walletservice.infrastructure.in.exceptions.InfoMessage;
import com.denismiagkov.walletservice.init.WebInit;
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
//@RequestMapping("/api")
public class Controller {
    /**
     * Cервис приложения
     */
    private Service service;

    /**
     * Конструктор класса
     */
    @Autowired
    public Controller(Service service) {
        this.service = service;
        WebInit.start();
    }

    /**
     * Метод вызывает в сервисе метод регистрации нового игрока. В зависимости от полученного результата
     * возвращает в консоль булевое значение.
     *
     * @param playerDto ДТО игрока
     * @return статус успеха регистрации
     */
    @PostMapping("/registration")
    public ResponseEntity<PlayerDto> registerPlayer(@RequestBody PlayerDto playerDto) throws RuntimeException {
        DataValidator.checkRegistrationForm(playerDto);
        service.registerPlayer(playerDto);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(playerDto);
    }


    /**
     * Метод вызывает в сервисе метод аутентентификации пользователя.     *
     *
     * @param entryDto ДТО учетной записи игрока, включающий его логин и пароль
     */
    @PostMapping("/authentication")
    public boolean authorizePlayer(@ModelAttribute("entryDto") EntryDto entryDto) throws RuntimeException {
        System.out.println(entryDto);
        return service.authorizePlayer(entryDto);
    }

    /**
     * Метод передает в сервис запрос о текущем состоянии баланса игрока.
     *
     * @param login идентификатор игрока (логин)
     */

    @PostMapping("/balance")
    public AccountDto getCurrentBalance(String login) {
        return service.getCurrentBalance(login);
    }

    /**
     * Метод вызывает в сервисе историю дебетовых и кредитных операций по счету игрока.
     *
     * @param login идентификатор игрока (логин)
     */
    @PostMapping("/transactions")
    public List<TransactionDto> getTransactionsHistory(String login) {
        return service.getTransactionHistory(login);
    }

    /**
     * Метод вызывает метод сервиса по пополнению денежного счета игрока.
     *
     * @param login  идентификатор игрока (логин)
     * @param amount сумма выполняемой операции
     */
    public boolean topUpAccount(String login, BigDecimal amount) {
        try {
            service.topUpAccount(login, amount);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Метод вызывает метод сервиса по списанию денежных средств со счета игрока.
     *
     * @param login  идентификатор игрока (логин)
     * @param amount сумма выполняемой операции
     */
    public void writeOffFunds(String login, BigDecimal amount) throws RuntimeException {
        service.writeOffFunds(login, amount);
    }

    /**
     * Метод вызывает в сервисе метод по фиксации в журнале аудита действия игрока по выходу из приложения.
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     */
    public void logExit(String login, String password) {
        service.logExit(login, password);
    }

}

