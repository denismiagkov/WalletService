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

    private Set<Player> allPlayers;
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

    public void setAllPlayers(Set<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public Map<String, String> getAllEntries() {
        return allEntries;
    }

    public void setAllEntries(Map<String, String> allEntries) {
        this.allEntries = allEntries;
    }

    public Map<String, Player> getAllPlayersLogins() {
        return loginsPerPlayers;
    }

    public void setAllPlayersLogins(Map<String, Player> allPlayersLogins) {
        this.loginsPerPlayers = allPlayersLogins;
    }

    public PlayerServiceImpl(Set<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    @Override
    public Player createPlayer(String firstName, String lastName, String email) {
        return new Player(firstName, lastName, email);
    }

    public void registerPlayer(String firstName, String lastName, String email, String login, String password) {
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
        }
    }

    public Player authorizePlayer(String login, String password) {
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
