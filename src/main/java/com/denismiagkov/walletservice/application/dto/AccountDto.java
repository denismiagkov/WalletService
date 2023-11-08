package com.denismiagkov.walletservice.application.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Сущность денежного счета")
@JsonPropertyOrder({"name", "surname", "number", "balance"})
public class AccountDto {
    @Schema(description = "Имя игрока")
    public String name;
    @Schema(description = "Фамилия игрока")
    public String surname;
    @Schema(description = "Номер счета")
    public String number;
    @Schema(description = "Теукщий баланс")
    public BigDecimal balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setAmount(BigDecimal balance) {
        this.balance = balance;
    }

    public String toString() {
        return "Account{" +
                "player: " + name +
                " " + surname +
                ", account number: " + number +
                ", balance: " + balance +
                '}' + "\n";
    }
}
