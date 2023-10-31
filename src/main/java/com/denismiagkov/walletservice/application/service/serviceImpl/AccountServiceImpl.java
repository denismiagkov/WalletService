package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.service.AccountService;
import com.denismiagkov.walletservice.repository.AccountDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

/**
 * Низкоуровневый сервис, реализующий методы, связанные с <strong>созданием денежного счета игрока,
 * просмотром текущего баланса и истории операций по нему</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
@org.springframework.stereotype.Service
public class AccountServiceImpl implements AccountService {

    /**
     * ДАО счета
     */
    AccountDAOImpl accountDAO;

    /**
     * Конструктор класса
     */
    @Autowired
    public AccountServiceImpl() {
        this.accountDAO = new AccountDAOImpl();
    }


    /**
     * Метод имитирует процесс присвоения номера создаваемому счету при регистрации игрока
     * путем использования генератора случайных чисел {@link AccountServiceImpl#createAccount(Player)}
     *
     * @return номер денежного счета
     */
    public String getAccountNumber() {
        while (true) {
            Random n = new Random();
            String number = String.valueOf(n.nextInt(899_000_000) + 100_000_000);
            return number;
        }
    }

    /**
     * Метод создает денежный счет. Применяется высокоуровневым сервисом при создании
     * и регистрации нового игрока {@link Service#registerPlayer(com.denismiagkov.walletservice.application.dto.PlayerDto)}
     *
     * @param player игрок, для которого создается денежный счет
     */
    @Override
    public void createAccount(Player player) {
        Account account = new Account(getAccountNumber());
        player.setAccount(account);
        accountDAO.saveAccount(player);
    }

    /**
     * Метод возвращает текущий баланс на счете игрока по его id
     *
     * @param playerId id игрока
     * @return денежный счет
     */
    @Override
    public Account getCurrentBalance(int playerId) {
        return accountDAO.getCurrentBalance(playerId);
    }

    /**
     * Метод сообщает историю дебетовых и кредитных операций по денежному счету игрока
     *
     * @param playerId идентификатор игрока, об истории транзакций которого запрашивается информация
     * @return список дебетовых и кредитных операций по счету игрока
     */
    @Override
    public List<Transaction> getTransactionHistory(int playerId) {
        return accountDAO.getTransactionHistory(playerId);
    }

    /**
     * Метод корректирует баланс на счете игрока после пополнения счета
     *
     * @param playerId id игрока
     * @param amount   денежная сумма
     */
    public void increaseBalance(int playerId, BigDecimal amount) {
        accountDAO.increaseBalance(playerId, amount);
    }

    /**
     * Метод корректирует баланс на счете игрока после списания средств
     *
     * @param playerId id игрока
     * @param amount   денежная сумма
     */
    public void decreaseBalance(int playerId, BigDecimal amount) {
        accountDAO.decreaseBalance(playerId, amount);
    }

    /**
     * Метод рассчитывает, достаточно ли денежных средств на счете игрока для их списания.
     *
     * @param playerId id игрока
     * @param amount   сумма списания
     * @return boolean
     * @throws NotEnoughFundsOnAccountException в случае, если на счете игрока недостаточно денежных средств для
     *                                          совершения транзакции
     */
    public boolean areFundsEnough(int playerId, BigDecimal amount) {
        if (getCurrentBalance(playerId).getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsOnAccountException();
        } else {
            return true;
        }
    }

    /**
     * Метод возвращает id денежного счета по id игрока
     *
     * @param playerId id игрока
     * @return id счета
     */
    public int getAccountId(int playerId) {
        return accountDAO.getAccountId(playerId);
    }
}
