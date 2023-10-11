package com.denismiagkov.walletservice.domain.service;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;

import java.sql.Timestamp;
import java.util.List;
/**
 *  * Интерфейс объявляет методы, реализующие бизнес-логику модели операции.
 * */
public interface OperationService {
    /**
     * Метод должен реализовывать занесение записи о действиях игрока в журнал аудита
     *
     * @param player игрок, совершивший действие
     * @param type тип действия
     * @param time время совершаения действия
     * @param status  статус действия
     * */
    void putOnLog(Player player, OperationType type, Timestamp time, OperationStatus status);
    List<Operation> viewLog();

}
