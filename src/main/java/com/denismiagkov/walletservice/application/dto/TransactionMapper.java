package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source = "time", target = "time")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "amount", target = "amount")
    TransactionDto toTransactionDto(Transaction transaction);

    List<TransactionDto> toTransactionDtoList(List<Transaction> transactions);
}
