package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMapperTest {

    @Test
    void toPlayerDto_ShouldReturnValidResult() {
        Player player = new Player("Ivan", "Petrov", "123@mail.ru");
        Entry entry = new Entry(1, "login", "password");
        PlayerMapper mapper = Mappers.getMapper(PlayerMapper.class);
        PlayerDto playerDto = mapper.INSTANCE.toPlayerDto(player, entry);
        assertAll(
                ()-> assertEquals("Ivan", playerDto.getName()),
                ()-> assertEquals("Petrov", playerDto.getSurname()),
                ()-> assertEquals("123@mail.ru", playerDto.getEmail()),
                ()-> assertEquals("login", playerDto.getLogin()),
                ()-> assertEquals("password", playerDto.getPassword())
        );
    }
}