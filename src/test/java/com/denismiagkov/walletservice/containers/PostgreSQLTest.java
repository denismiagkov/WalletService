package com.denismiagkov.walletservice.containers;

import com.denismiagkov.walletservice.containers.PostgreSQLTestContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PostgreSQLTest {

    PostgreSQLTestContainer container;


    @Test
    public void testConnection() {
        String jdbcUrl = PostgreSQLTestContainer.getJdbcUrl();
        String username = PostgreSQLTestContainer.getUsername();
        String password = PostgreSQLTestContainer.getPassword();

        // Проверяем подключение к базе данных
        Assertions.assertNotNull(jdbcUrl);
        Assertions.assertNotNull(username);
        Assertions.assertNotNull(password);
    }
}