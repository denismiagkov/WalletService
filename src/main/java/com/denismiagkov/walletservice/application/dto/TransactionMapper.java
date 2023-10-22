package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto toTransactionDto(Transaction transaction);

    List<Transaction> toTransactionDtoList(List<Transaction> transactions);
}
