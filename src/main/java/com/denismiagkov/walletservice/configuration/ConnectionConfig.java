package com.denismiagkov.walletservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
//@PropertySource("classpath:application.yml")
public class ConnectionConfig {
    private final String url;
    private final String username;
    private final String password;
    private final String driver;

    @Autowired
    public ConnectionConfig(@Value("${datasource.url}") String url,
                            @Value("${datasource.username}") String username,
                            @Value("${datasource.password}") String password,
                            @Value("${datasource.jdbc_driver}") String driver) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
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
