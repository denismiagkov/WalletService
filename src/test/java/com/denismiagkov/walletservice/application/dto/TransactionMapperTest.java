package com.denismiagkov.walletservice.application.dto;

import com.denismiagkov.walletservice.domain.model.Transaction;
import com.denismiagkov.walletservice.domain.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    TransactionMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(TransactionMapper.class);
    }

    @Test
    void toTransactionDto() {
        Transaction transaction = new Transaction(1, new Timestamp(System.currentTimeMillis()),
                TransactionType.DEBIT, BigDecimal.valueOf(500));
        TransactionDto transactionDto = mapper.INSTANCE.toTransactionDto(transaction);
        assertAll(
                ()-> assertNotNull(transactionDto),
                ()-> assertEquals(transaction.getTime(), transactionDto.getTime()),
                ()-> assertEquals(TransactionType.DEBIT, transactionDto.getType()),
                ()-> assertEquals(BigDecimal.valueOf(500), transactionDto.getAmount())
        );
    }

    @Test
    void toTransactionDtoList() {
        Transaction transaction1 = new Transaction(1, 111, new Timestamp(System.currentTimeMillis()),
                TransactionType.DEBIT, BigDecimal.valueOf(500));
        Transaction transaction2 = new Transaction(2, 222, new Timestamp(System.currentTimeMillis()),
                TransactionType.CREDIT, BigDecimal.valueOf(1000));
        TransactionDto transactionDto1 = mapper.INSTANCE.toTransactionDto(transaction1);
        TransactionDto transactionDto2 = mapper.INSTANCE.toTransactionDto(transaction1);
        List<Transaction> transactions = List.of(transaction1, transaction2);
        List<TransactionDto> list_Dto = mapper.INSTANCE.toTransactionDtoList(transactions);
        assertAll(
                ()-> assertNotNull(list_Dto),
                ()-> assertEquals(2, list_Dto.size()),
                ()-> assertEquals(transaction2.getTime(), list_Dto.get(1).getTime()),
                ()-> assertEquals(TransactionType.CREDIT, list_Dto.get(1).getType()),
                ()-> assertEquals(BigDecimal.valueOf(1000), list_Dto.get(1).getAmount())
        );
    }
}