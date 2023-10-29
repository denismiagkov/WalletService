package com.denismiagkov.walletservice.infrastructure.in.servlets;

import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.dto.PlayerMapper;
import com.denismiagkov.walletservice.application.service.serviceImpl.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountBalanceServletTest {
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsb2dpbjEiLCJleHAiOjE2OTgzODgxMDAsInVzZXJJZCI6MX0.J74p4eczxQ-" +
            "wwfaTSl0zWrStYAs-XejjfQuJPYgiP6nXe7SMrKbRRH-Z238nfl0bRpHNBEyl9QTOMQuSNnGdmg";
    PlayerDto playerDto = PlayerMapper.INSTANCE.toPlayerDto(
            new Player("Ivan", "Petrov", "petrov@mail.ru"),
            new Entry("user", "123"));

    @BeforeEach
    void setUp() {

    }

    @Test
    void doPostTest() {


    }

    @Test
    void doPost() {
    }
}