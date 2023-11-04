package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.init.DatabaseConnection;
import com.denismiagkov.walletservice.repository.interfaces.PlayerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;


/**
 * Класс отвечает за доступ к данным об игроках, хранящимся в базе данных. Предоставляет методы для создания,
 * чтения, обновления и удаления данных.
 */

@Repository
public class PlayerDAOImpl implements PlayerDAO {

    /**
     * Соединение с базой данных
     */
    DatabaseConnection dbConnection;
    private final String SELECT_ALL_FROM_PLAYERS;
    private final String SELECT_ALL_FROM_ENTRIES;


    /**
     * Конструктор класса
     *
     * @param dbConnection подключение к базе данных
     */
    @Autowired
    public PlayerDAOImpl(DatabaseConnection dbConnection,
                         @Value("SELECT * FROM wallet.players ") String selectAllPlayers,
                         @Value("SELECT * FROM wallet.entries ") String selectAllEntries) {

        this.dbConnection = dbConnection;
        this.SELECT_ALL_FROM_PLAYERS = selectAllPlayers;
        this.SELECT_ALL_FROM_ENTRIES = selectAllEntries;
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
            System.out.println("ENTERED INTO TRY");
            ResultSet rs = statement.executeQuery(SELECT_ALL_FROM_PLAYERS);
            Set<Player> allPlayers = new HashSet<>();
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                allPlayers.add(new Player(name, surname, email));
            }
            System.out.println(allPlayers);
            return allPlayers;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Метод возвращает список всех комбинаций логин-пароль, хранящихся в базе данных
     *
     * @throws SQLException
     */
    @Override
    public Map<String, String> getAllEntries() {
        System.out.println("DAO ENTRIES");
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SELECT_ALL_FROM_ENTRIES);
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
     * @param login логин игрока
     * @throws SQLException
     */

    public int getPlayerId(String login) {
        int playerId = -1;
        String queryPlayerId = "SELECT player_id FROM wallet.entries WHERE login = ?";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement prStatement = connection.prepareStatement(queryPlayerId);
            prStatement.setString(1, login);
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                playerId = rs.getInt("player_id");
            }
        } catch (SQLException e) {
            System.out.println("THIS IS ERROR: " + e.getMessage());
        }
        return playerId;
    }

    /**
     * Метод возвращает игрока по его id
     *
     * @param id id игрока
     * @return игрок
     */
    public Player getPlayerById(int id) {
        String getPlayerId = SELECT_ALL_FROM_PLAYERS + "WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(getPlayerId)) {
            prStatement.setInt(1, id);
            ResultSet rs = prStatement.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt("id");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String email = rs.getString("email");
                Player player = new Player(playerId, firstName, lastName, email);
                return player;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * Метод возвращает игрока по его логину
     *
     * @param login логин игрока
     * @return игрок
     */
    public Player getPlayerByLogin(String login) {
        String getPlayerId = SELECT_ALL_FROM_ENTRIES + "WHERE login = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(getPlayerId)) {
            prStatement.setString(1, login);
            ResultSet rs = prStatement.executeQuery();
            int playerId = 0;
            while (rs.next()) {
                playerId = rs.getInt("id");
            }
            Player player = getPlayerById(playerId);
            return player;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Метод возвращает комбинацию логин - пароль по логмну игрока
     *
     * @param login логин игрока
     * @return комбинация логин-пароль
     */

    public Entry getEntryByLogin(String login) {
        String getEntry = SELECT_ALL_FROM_ENTRIES + "WHERE login = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement prStatement = connection.prepareStatement(getEntry)) {
            prStatement.setString(1, login);
            ResultSet rs = prStatement.executeQuery();
            int playerId = 0;
            String password = null;
            while (rs.next()) {
                playerId = rs.getInt("id");
                password = rs.getString("password");
            }
            Entry entry = new Entry(playerId, login, password);
            return entry;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
