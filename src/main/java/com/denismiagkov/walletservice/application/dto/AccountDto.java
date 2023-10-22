package com.denismiagkov.walletservice.application.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({"name", "surname", "number", "amount"})
public class AccountDto {
    String name;
    String surname;
    String number;
    BigDecimal amount;
}
