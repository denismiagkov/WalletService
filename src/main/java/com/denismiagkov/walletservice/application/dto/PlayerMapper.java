package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "lastName", target = "surname")
    PlayerDto toPlayerDto(Player player);

    Set<PlayerDto> toPlayerDtoList(List<Player> players);

}
