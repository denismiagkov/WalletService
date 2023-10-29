package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.service.AccountService;
import com.denismiagkov.walletservice.repository.AccountDAOImpl;

import java.math.BigDecimal;
import java.util.*;

/**
 * Низкоуровневый сервис, реализующий методы, связанные с <strong>созданием денежного счета игрока,
 * просмотром текущего баланса и истории операций по нему</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
public class AccountServiceImpl implements AccountService {

    AccountDAOImpl adi;

    /**
     * Конструктор класса
     * */
    public AccountServiceImpl() {
        this.adi = new AccountDAOImpl();
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
            // if (!accountsInventory.contains(number)) {
                return number;
          //  }
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
        adi.saveAccount(player);
    }

    @Override
    public Account getCurrentBalance(int playerId) {
        return adi.getCurrentBalance(playerId);
    }

    /**
     * Метод сообщает историю дебетовых и кредитных операций по денежному счету игрока
     *
     * @param playerId идентификатор игрока, об истории транзакций которого запрашивается информация
     * @return список дебетовых и кредитных операций по счету игрока
     */
    @Override
    public List<Transaction> getTransactionHistory(int playerId) {
        return adi.getTransactionHistory(playerId);
    }

    public void increaseBalance(int playerId, BigDecimal amount){
        adi.increaseBalance(playerId, amount);
    }

    public void decreaseBalance(int playerId, BigDecimal amount){
        adi.decreaseBalance(playerId, amount);
    }

    /**
     * Метод рассчитывает, достаточно ли денежных средств на счете игрока для их списания.
     *
     * @param playerId id игрока
     * @param amount сумма списания
     * @throws NotEnoughFundsOnAccountException в случае, если на счете игрока недостаточно денежных средств для
     * совершения транзакции
     * @return boolean
     * */
    public boolean areFundsEnough(int playerId, BigDecimal amount) {
        if (getCurrentBalance(playerId).getBalance().compareTo(amount) < 0) {
            throw new NotEnoughFundsOnAccountException();
        } else {
            return true;
        }
    }

    public int getAccountId(int playerId){
       return adi.getAccountId(playerId);
    }

}
