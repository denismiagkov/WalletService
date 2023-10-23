package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.application.service.Service;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.IncorrectLoginException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.IncorrectPasswordException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.LoginIsNotUniqueException;
import com.denismiagkov.walletservice.application.service.serviceImpl.exception.PlayerAlreadyExistsException;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.service.PlayerService;
import com.denismiagkov.walletservice.repository.PlayerDAOImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Низкоуровневый сервис, реализующий методы, связанные с <strong>обработкой и манипуляцией данных об игроке</strong>.
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных операций,
 * соответствующих бизнес-логике.
 */
public class PlayerServiceImpl implements PlayerService {

    PlayerDAOImpl pdi;


    /**
     * Конструктор класса
     */
    public PlayerServiceImpl() {
        this.pdi = new PlayerDAOImpl();
    }

    /**
     * Метод создает игрока, уникальную комбинацию идентификатора (логина) и пароля, необходимую для проведения
     * аутентификации игрока при использовании приложения, "привязывает" данную комбинацию к игроку.
     *
     * @param firstName имя игрока
     * @param lastName  фамилия игрока
     * @param email     электронная почта игрока
     * @param login     уникальный идентификатор игрока (логин)
     * @param password  идентифицирующий признак игрока (пароль)
     * @return новый игрок
     * @throws PlayerAlreadyExistsException в случае, если у игрока уже имеется учетная запись в системе и он
     *                                      пытается зарегистрироваться повторно
     * @throws LoginIsNotUniqueException    в случае, если логин, предложенный пользователем в процессе регистрации,
     *                                      уже зарегистрирован в системе за другим игроком
     */
    @Override
    public Player registerPlayer(String firstName, String lastName, String email, String login, String password)
            throws RuntimeException {
        if (isPlayerExist(new Player(firstName, lastName, email))) {
            throw new PlayerAlreadyExistsException(firstName, lastName, email);
        } else if (isLoginExist(login)) {
            throw new LoginIsNotUniqueException(login);
        } else {
            Player player = new Player(firstName, lastName, email);
            player = pdi.savePlayer(player);
            Entry entry = new Entry(player.getId(), login, password);
            pdi.saveEntry(entry);
//
//
//            allPlayers.add(player);
//            allEntries.put(login, password);
//            loginsPerPlayers.put(login, player);
            return player;
        }
    }

    /**
     * Метод выполняет аутентификацию игрока путем сопоставления идентификатора (логина)
     * и идентифицирующего признака (пароля) для решения вопроса о доступе пользователя в систему
     * {@link Service#authorizePlayer(String, String)}.
     * *
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     * @return игрок, пытающийся войти в систему или совершить в ней определенное действие
     * @throws IncorrectLoginException    в случае, если пользователем введен логин, не зарегистрированный в системе
     * @throws IncorrectPasswordException в случае, если пользователем введен неверный пароль
     */
    public int authorizePlayer(String login, String password) throws RuntimeException {
        if (!isLoginExist(login)) {
            throw new IncorrectLoginException(login);
        } else if (isPasswordCorrect(login, password)) {
            int playerId = pdi.getPlayerId(login);
            return playerId;
        } else {
            throw new IncorrectPasswordException();
        }
    }


    public int getPlayerId(String login) {
        return pdi.getPlayerId(login);
    }

    private boolean isPlayerExist(Player player) {
        if (pdi.getAllPlayers().contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLoginExist(String login) {
        if (pdi.getAllEntries().containsKey(login)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordCorrect(String login, String password) {
        if (pdi.getAllEntries().get(login).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public Player getPlayerByLogin(String login){
        return pdi.getPlayerByLogin(login);
    }

}
