package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.application.aop.annotations.Loggable;
//import com.denismiagkov.walletservice.application.aop.aspects.LoggingAspect;
import com.denismiagkov.walletservice.application.dto.*;
import com.denismiagkov.walletservice.application.service.serviceImpl.*;
import com.denismiagkov.walletservice.domain.model.*;
import org.mapstruct.factory.Mappers;


import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Класс представляет основную бизнес-логику. Координирует и использует нижележащие сервисы
 * для модификации модели данных и предоставления конечных данных пользовательскому интерфейсу.
 */

public class Service {
    /**
     * Низкоуровневый сервис игрока
     */
    private PlayerServiceImpl psi;

    /**
     * Низкоуровневый сервис счета игрока
     */
    private AccountServiceImpl asi = new AccountServiceImpl();

    /**
     * Низкоуровневый сервис транзакции
     */
    private TransactionServiceImpl tsi;

    /**
     * Низкоуровневый сервис действия игрока
     */
    private OperationServiceImpl osi;

    private AccountMapper accountMapper;

   // private LoggingAspect loggingAspect;

    /**
     * Конструктор класса
     */
    public Service() {
        this.psi = new PlayerServiceImpl();
        this.asi = new AccountServiceImpl();
        this.tsi = new TransactionServiceImpl();
        this.osi = new OperationServiceImpl();
      //  this.loggingAspect = new LoggingAspect(osi, psi);
    }

    /**
     * Метод возвращает Низкоуровневый сервис игрока
     */
    public PlayerServiceImpl getPsi() {
        return psi;
    }

    /**
     * Метод возвращает Низкоуровневый сервис счета игрока
     */
    public AccountServiceImpl getAsi() {
        return asi;
    }

    /**
     * Метод возвращает Низкоуровневый сервис транзакции
     */
    public TransactionServiceImpl getTsi() {
        return tsi;
    }

    /**
     * Метод возвращает Низкоуровневый сервис действия игрока
     */
    public OperationServiceImpl getOsi() {
        return osi;
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
    public void registerPlayer(PlayerDto playerDto)
            throws RuntimeException {
        Player player = psi.registerPlayer(playerDto);
        asi.createAccount(player);
        osi.putOnLog(player.getId(), OperationType.REGISTRATION, new Timestamp(System.currentTimeMillis()),
                OperationStatus.SUCCESS);
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод выполняет аутентентификацию пользователя с фиксацией
     * результатов в журнале аудита. Метод пробрасывает исключения IncorrectLoginException, IncorrectPasswordException
     * на уровень контроллера.
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     * @see PlayerServiceImpl#authorizePlayer(String, String)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */
    @Loggable
    public boolean authorizePlayer(String login, String password) throws RuntimeException {
        int playerId = -1;
        try {
            playerId = psi.authorizePlayer(login, password);
            if (playerId > 0) {
                osi.putOnLog(psi.getPlayerId(login), OperationType.AUTHORIZATION,
                        new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            osi.putOnLog(psi.getPlayerId(login), OperationType.AUTHORIZATION,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
        }
        return false;
    }

//    /**
//     * Посредством вызова методов нижнеуровневых сервисов Метод передает игроку текущее состояние баланса на его счете
//     * с фиксацией статуса события в журнале аудита.
//     *
//     * @param playerId    идентификатор игрока (логин)
//     * @see PlayerServiceImpl#getPlayerId(String)
//     * @see AccountServiceImpl#getCurrentBalance(int)
//     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
//     */
//    public AccountDto getCurrentBalance(int playerId) {
//        try {
//            Account account = asi.getCurrentBalance(playerId);
//            osi.putOnLog(playerId, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
//                    OperationStatus.SUCCESS);
//            accountMapper = Mappers.getMapper(AccountMapper.class);
//            AccountDto accountDto = accountMapper.toAccountDto(player, account);
//            return accountDto;
//        } catch (Exception e) {
//            osi.putOnLog(playerId, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
//                    OperationStatus.FAIL);
//            return null;
//        }
//    }
    @Loggable
    public AccountDto getCurrentBalance(String login) {
        int playerId = psi.getPlayerId(login);
        try {
            Account account = asi.getCurrentBalance(playerId);
            osi.putOnLog(playerId, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.SUCCESS);
            Player player = psi.getPlayerByLogin(login);
            accountMapper = Mappers.getMapper(AccountMapper.class);
            AccountDto accountDto = accountMapper.toAccountDto(player, account);
            return accountDto;
        } catch (Exception e) {
            osi.putOnLog(playerId, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.FAIL);
            return null;
        }
    }


    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод передает игроку историю дебетовых и кредитных операций
     * по его счету с фиксацией статуса события в журнале аудита.
     *
     * @param login    идентификатор игрока (логин)
     * @see PlayerServiceImpl#getPlayerId(String)
     * @see AccountServiceImpl#getTransactionHistory(int)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */
    @Loggable
    public List<TransactionDto> getTransactionHistory(String login) {
        int playerId = psi.getPlayerId(login);
        try {
            List<Transaction> allTransactions = asi.getTransactionHistory(playerId);
            osi.putOnLog(playerId, OperationType.TRANSACTION_HISTORY_LOOKUP,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
            TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
            List<TransactionDto> transactionDtoList = transactionMapper.toTransactionDtoList(allTransactions);
            return transactionDtoList;
        } catch (Exception e) {
            osi.putOnLog(playerId, OperationType.TRANSACTION_HISTORY_LOOKUP,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            return null;
        }
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод выполняет пополнение денежного счета игрока
     * с фиксацией результата операции в журнале аудита. Метод пробрасывает исключение
     * NotUniqueTransactionIdException на уровень контроллера.
     *
     * @param login    идентификатор игрока (логин)
     * @param amount   сумма выполняемой операции
     */
    @Loggable
    public void topUpAccount(String login, BigDecimal amount)
            throws RuntimeException {
        int playerId = psi.getPlayerId(login);
        try {
            int accountId = asi.getAccountId(playerId);
            tsi.topUpAccount(accountId, amount);
            asi.increaseBalance(playerId, amount);
            osi.putOnLog(playerId, OperationType.CREDITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
        } catch (Exception e) {
            osi.putOnLog(playerId, OperationType.CREDITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            throw e;
        }
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод выполняет списание денежных средств со счета
     * игрока при наличии на счете необходимых средств с фиксацией результата операции в журнале аудита.
     * Метод пробрасывает исключения NotUniqueTransactionIdException и NotEnoughFundsOnAccountException
     * на уровень контроллера.
     *
     * @param login    идентификатор игрока (логин)
     * @param amount   сумма выполняемой операции
     * @see PlayerServiceImpl#getPlayerId(String)
     * @see TransactionServiceImpl#writeOffFunds(int, BigDecimal)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */
    @Loggable
    public void writeOffFunds(String login, BigDecimal amount)
            throws RuntimeException {
        int playerId = psi.getPlayerId(login);
        try {
            if (asi.areFundsEnough(playerId, amount)) {
                int accountId = asi.getAccountId(playerId);
                tsi.writeOffFunds(accountId, amount);
                asi.decreaseBalance(playerId, amount);
                osi.putOnLog(playerId, OperationType.DEBITING,
                        new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
            }
        } catch (Exception e) {
            osi.putOnLog(playerId, OperationType.DEBITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.FAIL);
            throw e;
        }
    }

    /**
     * Посредством вызова метода нижнеуровневого сервиса Метод обеспечивает фиксирование выхода игрока из
     * приложения в журнале аудита
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     * @see OperationServiceImpl#putOnLog(int, OperationType, Timestamp, OperationStatus)
     */
    public void logExit(String login, String password) {
        int playerId = psi.getPlayerId(login);
        osi.putOnLog(playerId, OperationType.EXIT, new Timestamp(System.currentTimeMillis()),
                OperationStatus.SUCCESS);
    }

    public Player getPlayerByLogin(String login){
        return psi.getPlayerByLogin(login);
    }

    public Map<String, String> getAllEntries(){
        return psi.getAllEntries();
    }

    public Entry getEntryByLogin(String login){
        return psi.getEntryByLogin(login);
    }
}




