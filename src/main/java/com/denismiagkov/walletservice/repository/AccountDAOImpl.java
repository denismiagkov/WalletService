package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.repository.interfaces.AccountDAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс отвечает за доступ к данным о счетах игроков, хранящимся в базе данных. Предоставляет методы для создания,
 * чтения, обновления и удаления сведений в базе данных.
 */
public class AccountDAOImpl implements AccountDAO {

    /**
     * Соединение с базой данных
     */
    DatabaseConnection dbConnection;

    /**
     * Конструктор класса
     */
    public AccountDAOImpl() {
        this.dbConnection = new DatabaseConnection();
    }

    /**
     * Конструктор класса с параметром(для тестирования)
     *
     * @param dbConnection подключение к базе данных
     * */
    public AccountDAOImpl(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Метод сохраняет данные о счете игрока в базе данных
     *
     * @param player Игрок
     * @throws SQLException
     */
    @Override
    public void saveAccount(Player player) {
        String insertAccount = "INSERT INTO wallet.accounts (number, balance, player_id) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertAccount)) {
            prStatement.setString(1, player.getAccount().getNumber());
            prStatement.setBigDecimal(2, player.getAccount().getBalance());
            prStatement.setInt(3, player.getId());
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод возвращает сведения о текущем балансе на счете заданного игрока
     *
     * @param playerId id игрока
     * @throws SQLException
     */
    public BigDecimal getCurrentBalance(int playerId) {
        String queryCurrentBalance = "SELECT balance FROM wallet.accounts WHERE player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryCurrentBalance)) {
            prStatement.setInt(1, playerId);
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                BigDecimal balance = rs.getBigDecimal("balance");
                return balance;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new BigDecimal(-1);
    }

    /**
     * Метод возвращает сведения о всех транзакциях, совершенных заданным игроком
     *
     * @param playerId id игрока
     * @return List<String>
     */
    public List<Transaction> getTransactionHistory(int playerId) {
        String queryTransactionHistory = "SELECT * FROM wallet.transactions " +
                "WHERE account = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryTransactionHistory)) {
            prStatement.setInt(1, playerId);
            List<Transaction> transactionHistory = new ArrayList<>();
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int accountId = rs.getInt("account");
                Timestamp time = rs.getTimestamp("commit_time");
                TransactionType type = TransactionType.valueOf(rs.getString("item_type"));
                BigDecimal amount = rs.getBigDecimal("amount");
                Transaction transaction = new Transaction(id, accountId, time, type, amount);
                transactionHistory.add(transaction);
            }
            return transactionHistory;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Метод рассчитывает баланс на счете игрока в случае его пополнения и передает полученное значение в метод
     * {@link #changeBalance(int, BigDecimal)} для последующей записи в базу данных
     *
     * @param playerId id игрока
     * @param amount   сумма пополнения счета
     */
    public void increaseBalance(int playerId, BigDecimal amount) {
        BigDecimal balance = getCurrentBalance(playerId).add(amount);
        changeBalance(playerId, balance);
    }

    /**
     * Метод рассчитывает баланс на счете игрока в случае списания средств и передает полученное значение в метод
     * {@link #changeBalance(int, BigDecimal)} для последующей записи в базу данных
     *
     * @param playerId id игрока
     * @param amount   сумма пополнения счета
     */
    public void decreaseBalance(int playerId, BigDecimal amount) {
        BigDecimal balance = getCurrentBalance(playerId).subtract(amount);
        changeBalance(playerId, balance);
    }

    /**
     * Метод сохраняет в базу данных новое состояние баланса в случае пополнения счета или списания средств со счета
     * игрока. Принимает значение из методов  {@link #increaseBalance(int, BigDecimal)} и
     * {@link #decreaseBalance(int, BigDecimal)}
     *
     * @param playerId id игрока
     * @param balance  новое состояния баланса после совершеннной транзакции
     */
    public void changeBalance(int playerId, BigDecimal balance) {
        String queryIncreaseBalance = "UPDATE wallet.accounts SET balance = ? WHERE player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryIncreaseBalance)) {
            prStatement.setBigDecimal(1, balance);
            prStatement.setInt(2, playerId);
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод возвращает id счета игрока.
     *
     * @param playerId id игрока
     * @return boolean
     */
    public int getAccountId(int playerId) {
        String queryGetAccountId = "SELECT id FROM wallet.accounts WHERE player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryGetAccountId)) {
            prStatement.setInt(1, playerId);
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int accountId = rs.getInt("id");
                return accountId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public Account getAccount(int playerId) {
        String queryGetAccountId = "SELECT * FROM wallet.accounts WHERE player_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryGetAccountId)) {
            prStatement.setInt(1, playerId);
            ResultSet rs = prStatement.executeQuery();
            Account account = new Account(playerId);
            while (rs.next()) {
                int accountId = rs.getInt("id");
                account.setId(accountId);
                String number = rs.getString("number");
                account.setNumber(number);
                BigDecimal balance = rs.getBigDecimal("balance");
                account.setBalance(balance);
            }
            return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
