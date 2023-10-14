package com.denismiagkov.walletservice.domain.model;

/**
 * Статус успеха действия игрока:
 * - действие выполнено успешно;
 * - неуспешная попытка совершения действия;
 * */
public enum OperationStatus {
    SUCCESS, FAIL;
}
