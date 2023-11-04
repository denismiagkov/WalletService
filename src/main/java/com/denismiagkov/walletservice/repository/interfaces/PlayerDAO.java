package com.denismiagkov.walletservice.repository.interfaces;

import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;

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
}
