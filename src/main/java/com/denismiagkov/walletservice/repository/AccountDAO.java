package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.domain.model.Player;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public interface AccountDAO {
    void saveAccount(Player player);

//    List<Player> getAllPlayers();
//    Player getPlayer();
//    void deletePlayer();
}
