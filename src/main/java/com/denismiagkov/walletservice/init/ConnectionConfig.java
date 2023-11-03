package com.denismiagkov.walletservice.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectionConfig {
    private final String url;
    private final String username;
    private final String password;
    private String driver;

    @Autowired
    public ConnectionConfig(@Value("${datasource.url}") String url,
                            @Value("${datasource.username}") String username,
                            @Value("${datasource.password}") String password,
                            @Value("${datasource.jdbc_driver}") String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}
