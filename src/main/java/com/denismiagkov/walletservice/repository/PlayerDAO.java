package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PlayerDAO {
    Player savePlayer(Player player);
    void saveEntry(Entry entry);

    Set<Player> getAllPlayers();

    Map<String, String> getAllEntries();
//
//
//    Player getPlayer();
//
//    void deletePlayer();
}
