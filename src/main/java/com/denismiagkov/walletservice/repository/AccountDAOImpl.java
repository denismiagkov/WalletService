package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.repository.interfaces.AccountDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<String> getTransactionHistory(int playerId) {
        String queryTransactionHistory = "SELECT commit_time, item_type, amount FROM wallet.transactions " +
                "WHERE account = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(queryTransactionHistory)) {
            prStatement.setInt(1, playerId);
            List<String> transactionHistory = new ArrayList<>();
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                String transaction = "{" + rs.getTimestamp("commit_time") +
                        " - " + rs.getString("item_type") +
                        " - " + rs.getFloat("amount") + "}";
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
}
