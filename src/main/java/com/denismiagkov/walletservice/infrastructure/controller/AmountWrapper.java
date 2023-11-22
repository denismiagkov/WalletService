package com.denismiagkov.walletservice.infrastructure.controller;

import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.DataValidator;
import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.IncorrectInputNumberException;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "'Класс-обертка' для суммы транзакции")
public class AmountWrapper {

    /**
     * Сумма транзакции
     */
    public String amount;

    /**
     * Метод проверяет формальную корректность введенной пользователем суммы и переводит ее в тип данных BigDecimal
     * для совершения последующих операций, либо выбрасывает исключение
     *
     * @return amount сумма транзакции
     * @throws IncorrectInputNumberException в случае ввода нечисловых или отрицательных числовых значений
     */
    public BigDecimal getAmount() throws IncorrectInputNumberException {
        DataValidator.checkTransactionAmount(amount);
        return new BigDecimal(amount);
    }

    /**
     * Метод устанавливает сумму транзакции для выполнения тестирования
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
