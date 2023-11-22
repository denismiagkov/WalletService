package com.denismiagkov.walletservice.repository.interfaces;

import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Интерфейс объявляет базовые методы, реализуемые репозиторием игрока
 */
public interface PlayerDAO {
    /**
     * Метод сохраняет игрока в баз данных.
     *
     * @see PlayerDAOImpl#savePlayer(Player)
     */
    Player savePlayer(Player player);

    /**
     * Метод сохраняет в базе данных значения логина и пароля игрока.
     *
     * @see PlayerDAOImpl#saveEntry(Entry)
     */
    void saveEntry(Entry entry);

    /**
     * Метод возвращает список игроков из базы данных.
     *
     * @see PlayerDAOImpl#getAllPlayers()
     */
    Set<Player> getAllPlayers();

    /**
     * Метод вохвращает из базы данных значения логиной и паролей всех игроков.
     *
     * @see PlayerDAOImpl#getAllEntries()
     */
    Map<String, String> getAllEntries();

    /**
     * Метод возвращает id заданного игрока по его логину и паролю
     *
     * @param login логин игрока
     * @throws SQLException
     */

    public int getPlayerId(String login);

    /**
     * Метод возвращает игрока по его логину
     *
     * @param login логин игрока
     * @return игрок
     */
    public Player getPlayerByLogin(String login);

    /**
     * Метод возвращает комбинацию логин - пароль по логмну игрока
     *
     * @param login логин игрока
     * @return комбинация логин-пароль
     */

    public Entry getEntryByLogin(String login);
}
