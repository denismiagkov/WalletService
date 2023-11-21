package com.denismiagkov.walletservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "authentication")
public class AuthConfig {
    private String valueOfJwtAccessSecretKey;
    private String valueOfJwtRefreshSecretKey;
}
