package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.application.service.serviceImpl.AccountServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.OperationServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.PlayerServiceImpl;
import com.denismiagkov.walletservice.application.service.serviceImpl.TransactionServiceImpl;
import com.denismiagkov.walletservice.domain.model.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Класс представляет основную бизнес-логику. Координирует и использует нижележащие сервисы
 * для модификации модели данных и предоставления конечных данных пользовательскому интерфейсу.
 */

public class Service {
    /**
     * Низкоуровневый сервис игрока
     * */
    private PlayerServiceImpl psi;

    /**
     * Низкоуровневый сервис счета игрока
     * */
    private AccountServiceImpl asi = new AccountServiceImpl();

    /**
     * Низкоуровневый сервис транзакции
     * */
    private TransactionServiceImpl tsi;

    /**
     * Низкоуровневый сервис действия игрока
     * */
    private OperationServiceImpl osi;

    /**
     * Конструктор класса
     * */
    public Service() {
        this.psi = new PlayerServiceImpl();
        this.asi = new AccountServiceImpl();
        this.tsi = new TransactionServiceImpl();
        this.osi = new OperationServiceImpl();
    }
/**
 * Метод возвращает Низкоуровневый сервис игрока
 * */
    public PlayerServiceImpl getPsi() {
        return psi;
    }

    /**
     * Метод возвращает Низкоуровневый сервис счета игрока
     * */
    public AccountServiceImpl getAsi() {
        return asi;
    }

    /**
     * Метод возвращает Низкоуровневый сервис транзакции
     * */
    public TransactionServiceImpl getTsi() {
        return tsi;
    }

    /**
     * Метод возвращает Низкоуровневый сервис действия игрока
     * */
    public OperationServiceImpl getOsi() {
        return osi;
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод создает нового игрока, создает для него
     * денежный счет и фиксирует операцию в журнале аудита. Метод пробрасывает исключения
     * PlayerAlreadyExistsException, LoginIsNotUniqueException на уровень контроллера.
     *
     * @param firstName имя игрока
     * @param lastName  фамилия игрока
     * @param email     электронная почта игрока
     * @param login     уникальный идентификатор игрока (логин)
     * @param password  идентифицирующий признак игрока (пароль)
     * @see PlayerServiceImpl#registerPlayer(String, String, String, String, String)
     * @see AccountServiceImpl#createAccount(Player)
     * @see OperationServiceImpl#putOnLog(Player, OperationType, Timestamp, OperationStatus)
     */
    public void registerPlayer(String firstName, String lastName, String email, String login, String password)
            throws RuntimeException {
        Player player = psi.registerPlayer(firstName, lastName, email, login, password);
        asi.createAccount(player);
        osi.putOnLog(player, OperationType.REGISTRATION, new Timestamp(System.currentTimeMillis()),
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
     * @see OperationServiceImpl#putOnLog(Player, OperationType, Timestamp, OperationStatus)
     */
    public boolean authorizePlayer(String login, String password) throws RuntimeException {
        Player player = null;
        try {
            player = psi.authorizePlayer(login, password);
            if (player != null) {
                osi.putOnLog(player, OperationType.AUTHORIZATION, new Timestamp(System.currentTimeMillis()),
                        OperationStatus.SUCCESS);
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            osi.putOnLog(player, OperationType.AUTHORIZATION, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.FAIL);
        }
        return false;
    }

    /**
     * Посредством вызова метода нижнеуровневого сервиса Метод возвращает игрока по введенным логину и паролю.
     * Результат метода используется при вызове других методов сервиса для установления личности игрока.
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     * @see PlayerServiceImpl#authorizePlayer(String, String)
     */
    public Player getPlayer(String login, String password) throws RuntimeException {
        return psi.authorizePlayer(login, password);
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод передает игроку текущее состояние баланса на его счете
     * с фиксацией статуса события в журнале аудита.
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     * @see Service#getPlayer(String, String)
     * @see AccountServiceImpl#getCurrentBalance(Player)
     * @see OperationServiceImpl#putOnLog(Player, OperationType, Timestamp, OperationStatus)
     */
    public BigDecimal getCurrentBalance(String login, String password) {
        Player player = getPlayer(login, password);
        try {
            BigDecimal balance = asi.getCurrentBalance(player);
            osi.putOnLog(player, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.SUCCESS);
            return balance;
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.BALANCE_LOOKUP, new Timestamp(System.currentTimeMillis()),
                    OperationStatus.FAIL);
            return null;
        }
    }

    /**
     * Посредством вызова методов нижнеуровневых сервисов Метод передает игроку историю дебетовых и кредитных операций
     * по его счету с фиксацией статуса события в журнале аудита.
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     * @see Service#getPlayer(String, String)
     * @see AccountServiceImpl#showTransactionsHistory(Player)
     * @see OperationServiceImpl#putOnLog(Player, OperationType, Timestamp, OperationStatus)
     */
    public List<Transaction> getTransactionsHistory(String login, String password) {
        Player player = getPlayer(login, password);
        try {
            List<Transaction> transactionsHistory = asi.showTransactionsHistory(player);
            osi.putOnLog(player, OperationType.TRANSACTION_HISTORY_LOOKUP,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
            return transactionsHistory;
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.TRANSACTION_HISTORY_LOOKUP,
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
     * @param password идентифицирующий признак игрока (пароль)
     * @param uniqueId идентификатор транзакции, предоставляемый пользователем
     * @param amount   сумма выполняемой операции
     */
    public void topUpAccount(String login, String password, String uniqueId, BigDecimal amount)
            throws RuntimeException {
        Player player = getPlayer(login, password);
        try {
            tsi.topUpAccount(uniqueId, player.getAccount(), amount);
            osi.putOnLog(player, OperationType.CREDITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.CREDITING,
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
     * @param password идентифицирующий признак игрока (пароль)
     * @param uniqueId идентификатор транзакции, предоставляемый пользователем
     * @param amount   сумма выполняемой операции
     * @see Service#getPlayer(String, String)
     * @see TransactionServiceImpl#writeOffFunds(String, Account, BigDecimal)
     * @see OperationServiceImpl#putOnLog(Player, OperationType, Timestamp, OperationStatus)
     */
    public void writeOffFunds(String login, String password, String uniqueId, BigDecimal amount)
            throws RuntimeException {
        Player player = getPlayer(login, password);
        try {
            tsi.writeOffFunds(uniqueId, player.getAccount(), amount);
            osi.putOnLog(player, OperationType.DEBITING,
                    new Timestamp(System.currentTimeMillis()), OperationStatus.SUCCESS);
        } catch (Exception e) {
            osi.putOnLog(player, OperationType.DEBITING,
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
     * @see OperationServiceImpl#putOnLog(Player, OperationType, Timestamp, OperationStatus)
     */
    public void logExit(String login, String password) {
        Player player = getPlayer(login, password);
        osi.putOnLog(player, OperationType.EXIT, new Timestamp(System.currentTimeMillis()),
                OperationStatus.SUCCESS);
        //System.out.println(osi.getLog());
    }
}




