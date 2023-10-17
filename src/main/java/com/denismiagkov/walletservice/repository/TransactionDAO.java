package com.denismiagkov.walletservice.repository;

import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

public interface TransactionDAO {
    void saveTransaction(Transaction transaction);

//    Set<Player> getAllPlayers();

//    Map<String, String> getAllEntries();
//
//
//    Player getPlayer();
//
//    void deletePlayer();
}
