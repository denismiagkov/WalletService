package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Player;

public interface PlayerService {

     Player registerPlayer(String firstName, String lastName, String email, String login, String password);

     Player authorizePlayer (String login, String password);
}
