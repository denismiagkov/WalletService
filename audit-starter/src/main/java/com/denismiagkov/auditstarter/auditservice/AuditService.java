package com.denismiagkov.auditstarter.auditservice;

import com.denismiagkov.auditstarter.operation.Operation;
import com.denismiagkov.auditstarter.operation.OperationStatus;
import com.denismiagkov.auditstarter.operation.OperationType;

import java.sql.Timestamp;
import java.util.List;
/**
 *  * Интерфейс объявляет методы, реализующие бизнес-логику модели операции.
 * */
public interface AuditService {
    /**
     * Метод должен реализовывать занесение записи о действиях игрока в журнал аудита
     *
     * @param playerId идентификатор игрока, совершившего действие
     * @param type тип действия
     * @param time время совершаения действия
     * @param status  статус действия
     * */
    void putOnLog(int playerId, OperationType type, Timestamp time, OperationStatus status);

    /**
     * Метод должен реализовать просмотр журнала действий
     * */
    List<Operation> viewLog();

}
