package com.denismiagkov.auditstarter.operation;


/**
 * Исчерпывающий перечень типов действий, выполняемых игроком:
 * - авторизация (вход игрока в систему);
 * - просмотр баланса счета;
 * - пополнение баланса счета;
 * - списание денежных средств со счета;
 * - выход из программы;
 * - регистрация игрока;
 * - просмотр истории транзакций по счету;
 * */
public enum OperationType {
    AUTHORIZATION, BALANCE_LOOKUP, CREDITING,  DEBITING,  EXIT, REGISTRATION,TRANSACTION_HISTORY_LOOKUP
}
