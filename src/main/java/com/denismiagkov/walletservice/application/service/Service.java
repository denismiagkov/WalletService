package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.application.dto.*;
import com.denismiagkov.walletservice.application.service.serviceImpl.*;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.domain.model.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.List;

/**
 * Класс представляет основную бизнес-логику. Координирует и использует нижележащие сервисы
 * для модификации модели данных и предоставления конечных данных пользовательскому интерфейсу.
 */

@org.springframework.stereotype.Service
public class Service {
    /**
     * Низкоуровневый сервис игрока
     */
    private PlayerServiceImpl playerService;

    /**
     * Низкоуровневый сервис счета игрока
     */
    private AccountServiceImpl accountService;

    /**
     * Низкоуровневый сервис транзакции
     */
    private TransactionServiceImpl transactionService;

    /**
     * Низкоуровневый сервис действия игрока
     */
    private OperationServiceImpl operationService;

    /**
     * Конструктор класса
     */
    @Autowired
    public Service(PlayerServiceImpl playerService, AccountServiceImpl accountService,
                   TransactionServiceImpl transactionService, OperationServiceImpl operationService) {
        this.playerService = playerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.operationService = operationService;
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод создает нового игрока, создает для него
     * денежный счет и фиксирует операцию в журнале аудита. Метод пробрасывает исключения
     * PlayerAlreadyExistsException, LoginIsNotUniqueException на уровень контроллера.
     *
     * @param playerDto ДТО игрока
     * @see AccountServiceImpl#createAccount(Player)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */
    public void registerPlayer(PlayerDto playerDto) throws RuntimeException {
        Player player = playerService.registerPlayer(playerDto);
        accountService.createAccount(player);
        operationService.putOnLog(player.getId(), OperationType.REGISTRATION, new Timestamp(System.currentTimeMillis()),
                OperationStatus.SUCCESS);
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод передает пользователю текущее состояние баланса
     * на его счете с фиксацией статуса события в журнале аудита.
     *
     * @param login идентификатор игрока (логин)
     * @see PlayerServiceImpl#getPlayerId(String)
     * @see AccountServiceImpl#getCurrentBalance(int)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */
    public AccountDto getCurrentBalance(String login) {
        int playerId = playerService.getPlayerId(login);
        try {
            Account account = accountService.getCurrentBalance(playerId);
            operationService.putOnLog(playerId, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.SUCCESS);
            Player player = playerService.getPlayerByLogin(login);
            AccountDto accountDto = AccountMapper.INSTANCE.toAccountDto(player, account);
            return accountDto;
        } catch (Exception e) {
            operationService.putOnLog(playerId, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.FAIL);
            return null;
        }
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод передает игроку историю дебетовых и кредитных операций
     * по его счету с фиксацией статуса события в журнале аудита.
     *
     * @param login идентификатор игрока (логин)
     * @see PlayerServiceImpl#getPlayerId(String)
     * @see AccountServiceImpl#getTransactionHistory(int)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */
    public List<TransactionDto> getTransactionHistory(String login) {
        int playerId = playerService.getPlayerId(login);
        try {
            List<Transaction> allTransactions = accountService.getTransactionHistory(playerId);
            operationService.putOnLog(playerId, OperationType.TRANSACTION_HISTORY_LOOKUP,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
            List<TransactionDto> transactionDtoList = TransactionMapper.INSTANCE.toTransactionDtoList(allTransactions);
            return transactionDtoList;
        } catch (Exception e) {
            operationService.putOnLog(playerId, OperationType.TRANSACTION_HISTORY_LOOKUP,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            return null;
        }
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод выполняет пополнение денежного счета игрока
     * с фиксацией результата операции в журнале аудита
     *
     * @param login  идентификатор игрока (логин)
     * @param amount сумма выполняемой операции
     */

    public void topUpAccount(String login, BigDecimal amount) {
        int playerId = playerService.getPlayerId(login);
        try {
            int accountId = accountService.getAccountId(playerId);
            transactionService.topUpAccount(accountId, amount);
            accountService.increaseBalance(playerId, amount);
            operationService.putOnLog(playerId, OperationType.CREDITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
        } catch (Exception e) {
            operationService.putOnLog(playerId, OperationType.CREDITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
        }
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод выполняет списание денежных средств со счета
     * игрока при наличии на счете необходимых средств с фиксацией результата операции в журнале аудита.
     * Метод пробрасывает исключение NotEnoughFundsOnAccountException на уровень контроллера.
     *
     * @param login  идентификатор игрока (логин)
     * @param amount сумма выполняемой операции
     * @see PlayerServiceImpl#getPlayerId(String)
     * @see TransactionServiceImpl#writeOffFunds(int, BigDecimal)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */

    public void writeOffFunds(String login, BigDecimal amount) throws RuntimeException {
        int playerId = playerService.getPlayerId(login);
        try {
            if (accountService.areFundsEnough(playerId, amount)) {
                int accountId = accountService.getAccountId(playerId);
                transactionService.writeOffFunds(accountId, amount);
                accountService.decreaseBalance(playerId, amount);
                operationService.putOnLog(playerId, OperationType.DEBITING,
                        new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
            } else {
                throw new NotEnoughFundsOnAccountException();
            }
        } catch (Exception e) {
            operationService.putOnLog(playerId, OperationType.DEBITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            throw e;
        }
    }

    /**
     * Метод возвращает игрока по его логину
     *
     * @param login идентификатор игрока (логин)
     * @return игрок
     * @see com.denismiagkov.walletservice.infrastructure.login_service.JwtProvider#generateAccessToken(Entry)
     */
    public Player getPlayerByLogin(String login) {
        return playerService.getPlayerByLogin(login);
    }

    /**
     * Метод возвращает комбинацию логин - пароль по логину игрока
     *
     * @param login идентификатор игрока (логин)
     * @return комбинация логин-пароль игрока
     */
    public Entry getEntryByLogin(String login) {
        return playerService.getEntryByLogin(login);
    }
}




