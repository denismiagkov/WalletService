package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Player;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PlayerDAO {
    void savePlayer(Connection connection, Player player);

//    List<Player> getAllPlayers();
//
//
//    Player getPlayer();
//
//    void deletePlayer();
}
