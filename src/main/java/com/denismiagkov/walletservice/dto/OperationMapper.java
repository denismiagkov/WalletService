package com.denismiagkov.walletservice.dto;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface OperationMapper {
    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

//    @Mapping(source = "player.firstName", target = "name")
//    @Mapping(source = "player.lastName", target = "surname")
    OperationDto toOperationDto(Operation operation);

    List<OperationDto> toOperationDtoList(List<Operation> operations);

}
