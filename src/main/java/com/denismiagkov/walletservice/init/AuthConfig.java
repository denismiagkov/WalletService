package com.denismiagkov.walletservice.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthConfig {
    private final String valueOfJwtAccessSecretKey;
    private final String valueOfJwtRefreshSecretKey;

    @Autowired
    public AuthConfig(@Value("${authentication.jwt_access_secret_key}") String valueOfJwtAccessSecretKey,
                      @Value("${authentication.jwt_refresh_secret_key}") String valueOfJwtRefreshSecretKey) {
        this.valueOfJwtAccessSecretKey = valueOfJwtAccessSecretKey;
        this.valueOfJwtRefreshSecretKey = valueOfJwtRefreshSecretKey;
    }

    public String getValueOfJwtAccessSecretKey() {
        return valueOfJwtAccessSecretKey;
    }

    public String getValueOfJwtRefreshSecretKey() {
        return valueOfJwtRefreshSecretKey;
    }
}
