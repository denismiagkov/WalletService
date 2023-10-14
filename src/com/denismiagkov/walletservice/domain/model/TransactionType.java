package com.denismiagkov.walletservice.domain.model;
/**
 * Исчерпывающий перечень типов транзакций:
 * - дебетовая;
 * - кредитная
 * */
public enum TransactionType {
    DEBIT, CREDIT;
}
