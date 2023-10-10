package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Player;

public interface PlayerService {
     Player createPlayer(String firstName, String lastName, String email);
}
