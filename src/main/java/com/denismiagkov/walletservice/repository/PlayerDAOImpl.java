package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.DatabaseConnection;
import com.denismiagkov.walletservice.repository.interfaces.PlayerDAO;

import java.sql.*;
import java.util.*;


/**
 * Класс отвечает за доступ к данным об игроках, хранящимся в базе данных. Предоставляет методы для создания,
 * чтения, обновления и удаления данных.
 */
public class PlayerDAOImpl implements PlayerDAO {

    /**
     * Соединение с базой данных
     */
    DatabaseConnection dbConnection;

    /**
     * Конструктор класса
     */
    public PlayerDAOImpl() {
        this.dbConnection = new DatabaseConnection();
    }

    public PlayerDAOImpl(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Метод сохраняет данные об игроке в базе данных
     *
     * @param player Игрок
     * @return player Игрок
     */
    @Override
    public Player savePlayer(Player player) {
        String insertPlayer = "INSERT INTO wallet.players (name, surname, email) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertPlayer)) {
            prStatement.setString(1, player.getFirstName());
            prStatement.setString(2, player.getLastName());
            prStatement.setString(3, player.getEmail());
            prStatement.executeUpdate();
            player.setId(getPlayerId(player));
            return player;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * Метод сохраняет данные о логине и пароле игрока в базе данных
     *
     * @param entry данные о логине и пароле игрока
     */
    @Override
    public void saveEntry(Entry entry) {
        String insertEntry = "INSERT INTO wallet.entries (login, password, player_id) VALUES (?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(insertEntry)) {
            prStatement.setString(1, entry.getLogin());
            prStatement.setString(2, entry.getPassword());
            prStatement.setInt(3, entry.getPlayerId());
            prStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод возвращает список всех игроков, зарегистрированных в приложении
     *
     * @return Set<Player>
     */
    @Override
    public Set<Player> getAllPlayers() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM wallet.players");
            Set<Player> allPlayers = new HashSet<>();
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                allPlayers.add(new Player(name, surname, email));
            }
            return allPlayers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
       // playerDAO.savePlayer(new Player("den", "ryeman", "tomsk"));
        System.out.println(playerDAO.getAllPlayers());
    }

    /**
     * Метод возвращает список всех комбинаций логин-пароль, хранящихся в базе данных
     *
     * @throws SQLException
     */
    @Override
    public Map<String, String> getAllEntries() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM wallet.entries");
            Map<String, String> allEntries = new HashMap<>();
            while (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                allEntries.put(login, password);
            }
            return allEntries;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Метод возвращает id заданного игрока
     *
     * @param player Игрок
     * @throws SQLException
     */
    public int getPlayerId(Player player) {
        String getPlayerId = "SELECT id FROM wallet.players WHERE name = ? AND surname = ? and email = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(getPlayerId)) {
            prStatement.setString(1, player.getFirstName());
            prStatement.setString(2, player.getLastName());
            prStatement.setString(3, player.getEmail());
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt("id");
                return playerId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }

    /**
     * Метод возвращает id заданного игрока по его логину и паролю
     *
     * @param login    логин игрока
     * @param password пароль игрока
     * @throws SQLException
     */
    public int getPlayerId(String login, String password) {
        String queryPlayerId = "SELECT player_id FROM wallet.entries WHERE login = ? AND password = ?";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement(queryPlayerId);
            prStatement.setString(1, login);
            prStatement.setString(2, password);
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt("player_id");
                return playerId;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

//    public void deletePlayer(int id) {
//        String deletePlayer = "DELETE FROM wallet.players WHERE name =?";
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement prStatement = connection.prepareStatement(deletePlayer)) {
//            prStatement.setId(1, id);
//            prStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
