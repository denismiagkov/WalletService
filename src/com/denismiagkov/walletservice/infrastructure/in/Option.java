package com.denismiagkov.walletservice.infrastructure.in;

public interface Option {
    String getDescription();
    void execute();
    void execute(String login, String password);
}