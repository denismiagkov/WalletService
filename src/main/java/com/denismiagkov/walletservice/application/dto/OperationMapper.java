package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OperationMapper {
    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

//    @Mapping(source = "player.firstName", target = "name")
//    @Mapping(source = "player.lastName", target = "surname")
    OperationDto toOperationDto(Operation operation);

    List<OperationDto> toOperationDtoList(List<Operation> operations);

}
