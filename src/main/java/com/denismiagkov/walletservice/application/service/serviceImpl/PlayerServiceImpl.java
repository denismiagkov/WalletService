package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.LoginIsNotUniqueException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.PlayerAlreadyExistsException;
import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.service.PlayerService;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;
import com.denismiagkov.walletservice.repository.interfaces.PlayerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Низкоуровневый сервис, реализующий методы, связанные с <strong>обработкой и манипуляцией данных об игроке</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных операций,
 * соответствующих бизнес-логике.
 */
@Service
public class PlayerServiceImpl implements PlayerService {
    /**
     * ДАО игрока
     */
    PlayerDAO playerDAO;

    /**
     * Конструктор класса
     */
    @Autowired
    public PlayerServiceImpl(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    /**
     * Метод создает игрока, уникальную комбинацию идентификатора (логина) и пароля, необходимую для проведения
     * аутентификации игрока при использовании приложения, "привязывает" данную комбинацию к игроку.
     *
     * @param playerDto ДТО игрока (данные, полученные от пользователя при регистрации)
     * @return новый игрок
     * @throws PlayerAlreadyExistsException в случае, если у игрока уже имеется учетная запись в системе и он
     *                                      пытается зарегистрироваться повторно
     * @throws LoginIsNotUniqueException    в случае, если логин, предложенный пользователем в процессе регистрации,
     *                                      уже зарегистрирован в системе за другим игроком
     */
    @Override
    public Player registerPlayer(PlayerDto playerDto) throws RuntimeException {
        if (isPlayerExist(new Player(playerDto.getName(), playerDto.getSurname(), playerDto.getEmail()))) {
            throw new PlayerAlreadyExistsException(playerDto.getName(), playerDto.getSurname(), playerDto.getEmail());
        } else if (isLoginExist(playerDto.getLogin())) {
            throw new LoginIsNotUniqueException(playerDto.getLogin());
        } else {
            Player player = new Player(playerDto.getName(), playerDto.getSurname(), playerDto.getEmail());
            player = playerDAO.savePlayer(player);
            Entry entry = new Entry(player.getId(), playerDto.getLogin(), playerDto.getPassword());
            playerDAO.saveEntry(entry);
            return player;
        }
    }

    /**
     * Метод возвращает булевое значение, зарегистрирован ли определенный игрок в приложении
     *
     * @param player игрок
     * @return булевое значение
     */
    public boolean isPlayerExist(Player player) {
        return playerDAO.getAllPlayers().contains(player);
    }

    /**
     * Метод возвращает булевое значение, занят ли определенный логин в приложении
     *
     * @param login логин игрока
     * @return булевое значение
     */
    public boolean isLoginExist(String login) {
        return playerDAO.getAllEntries().containsKey(login);
    }

    /**
     * Метод возвращает id игрока по его логину
     *
     * @param login логин игрока
     * @return int id игрока
     */
    public int getPlayerId(String login) {
        return playerDAO.getPlayerId(login);
    }

    /**
     * Метод возвращает игрока по его логину
     *
     * @param login логин игрока
     * @return игрок
     */
    public Player getPlayerByLogin(String login) {
        return playerDAO.getPlayerByLogin(login);
    }

    /**
     * Метод возвращает комбинацию логин-пароль по логину игрока
     *
     * @param login логин игрока
     * @return комбинация логин-пароль
     */
    public Entry getEntryByLogin(String login) {
        return playerDAO.getEntryByLogin(login);
    }
}
