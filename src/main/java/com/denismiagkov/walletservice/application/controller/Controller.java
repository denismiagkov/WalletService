package com.denismiagkov.walletservice.application.controller;

import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.dto.TransactionDto;
import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.OperationServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Класс обрабатывает запросы, полученные от пользователя и управляет взаимодействием между внешним
 * и внутренними слоями приложения
 */
public class Controller {
    /**
     * Cервис приложения
     */
    private Service service;

    /**
     * Конструктор класса
     */
    public Controller(Service service) {
        this.service = service;
    }

    /**
     * Метод вызывает в сервисе метод регистрации нового игрока. В зависимости от полученного результата
     * возвращает в консоль булевое значение.
     *
     * @param playerDto ДТО игрока
     * @return статус успеха регистрации
     */
    public boolean registerPlayer(PlayerDto playerDto) {
        boolean isSuccessful = false;
        try {
            service.registerPlayer(playerDto);
            isSuccessful = true;
        } catch (RuntimeException e) {
        }
        return isSuccessful;
    }

    /**
     * Метод вызывает в сервисе метод аутентентификации пользователя.     *
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     */
    public boolean authorizePlayer(String login, String password) {
        return service.authorizePlayer(login, password);
    }

    /**
     * Метод передает в сервис запрос о текущем состоянии баланса игрока.
     *
     * @param login    идентификатор игрока (логин)
     */
    public AccountDto getCurrentBalance(String login) {
        return service.getCurrentBalance(login);
    }

    /**
     * Метод вызывает в сервисе историю дебетовых и кредитных операций по счету игрока.
     *
     * @param login    идентификатор игрока (логин)
     */
    public List<TransactionDto> getTransactionsHistory(String login) {
        return service.getTransactionHistory(login);
    }

    /**
     * Метод вызывает метод сервиса по пополнению денежного счета игрока.
     *
     * @param login    идентификатор игрока (логин)
     * @param amount   сумма выполняемой операции
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
     * @param login    идентификатор игрока (логин)
     * @param amount   сумма выполняемой операции
     */
    public boolean writeOffFunds(String login, BigDecimal amount) {
        try {
            service.writeOffFunds(login, amount);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
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

