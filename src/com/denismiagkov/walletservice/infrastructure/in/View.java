package com.denismiagkov.walletservice.infrastructure.in;

import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.infrastructure.in.menu.Menu;

public interface View {
        void print(String text);
        void start();
        void startProfile(String login, String password);
}
