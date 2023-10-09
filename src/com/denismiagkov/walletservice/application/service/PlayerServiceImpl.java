package com.denismiagkov.walletservice.application.service;

import com.denismiagkov.walletservice.application.service.exception.IncorrectLoginException;
import com.denismiagkov.walletservice.application.service.exception.IncorrectPasswordException;
import com.denismiagkov.walletservice.application.service.exception.LoginIsNotUniqueException;
import com.denismiagkov.walletservice.application.service.exception.PlayerAlreadyExistsException;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.service.PlayerService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerServiceImpl implements PlayerService {

    public Set<Player> allPlayers;
    private Map<String, String> allEntries;
    private Map<String, Player> loginsPerPlayers;

    public PlayerServiceImpl() {
        this.allPlayers = new HashSet<>();
        this.allEntries = new HashMap<>();
        this.loginsPerPlayers = new HashMap<>();
    }

    public Set<Player> getAllPlayers() {
        return allPlayers;
    }

    public Map<String, String> getAllEntries() {
        return allEntries;
    }

    public Map<String, Player> getLoginsPerPlayers() {
        return loginsPerPlayers;
    }

    @Override
    public Player createPlayer(String firstName, String lastName, String email) {
        return new Player(firstName, lastName, email);
    }

    public Player registerPlayer(String firstName, String lastName, String email, String login, String password)
            throws RuntimeException {
        if (allPlayers.contains(new Player(firstName, lastName, email))) {
            throw new PlayerAlreadyExistsException(firstName, lastName, email);
        } else if (allEntries.containsKey(login)) {
            throw new LoginIsNotUniqueException(login);
        } else {
            Player player = createPlayer(firstName, lastName, email);
            Entry entry = new Entry(player, login, email);
            allPlayers.add(player);
            allEntries.put(login, password);
            loginsPerPlayers.put(login, player);
            return player;
        }
    }

    public Player authorizePlayer(String login, String password) throws RuntimeException {
        if (!allEntries.containsKey(login)) {
            throw new IncorrectLoginException(login);
        } else if (allEntries.get(login).equals(password)) {
            Player player = loginsPerPlayers.get(login);
            return player;
        } else {
            throw new IncorrectPasswordException();
        }

    }
}
