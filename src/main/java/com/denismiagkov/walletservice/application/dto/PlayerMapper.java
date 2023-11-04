package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Entry;
import com.denismiagkov.walletservice.domain.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(source = "player.firstName", target = "name")
    @Mapping(source = "player.lastName", target = "surname")
    @Mapping(source = "player.email", target = "email")
    @Mapping(source = "entry.login", target = "login")
    @Mapping(source = "entry.password", target = "password")
    PlayerDto toPlayerDto(Player player, Entry entry);
}
