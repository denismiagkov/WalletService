package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Account;
import com.denismiagkov.walletservice.domain.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    @Mapping(source = "player.firstName", target = "name")
    @Mapping(source = "player.lastName", target = "surname")
    @Mapping(source = "account.number", target = "number")
    @Mapping(source = "account.balance", target = "balance")
    AccountDto toAccountDto(Player player, Account account);

    Set<PlayerDto> toPlayerDtoList(List<Player> players);


}
