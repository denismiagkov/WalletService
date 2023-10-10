package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.model.Player;
import com.denismiagkov.walletservice.domain.service.OperationService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Низкоуровневый сервис, реализующий методы <strong>для реализации аудита действий игрока</strong>.
 * Перечень фиксируемых действий устанавливается в классе {@link OperationType}
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
public class OperationServiceImpl implements OperationService {

    /**
     * Перечень всех отслеживаемых действий, совершенных игроками
     */
    private List<Operation> log;

    public OperationServiceImpl() {
        this.log = new ArrayList<>();
    }

    public List<Operation> getLog() {
        return log;
    }

    /**
     * Метод записывает в журнал атрибуты действия, совершенного игроком.
     *
     * @param player игрок, совершивший действие
     * @param type   вид совершенного действия (из перечня {@link OperationType}
     * @param time   время совершения действия
     * @param status успех/неуспех совершенного действия
     */
    @Override
    public void putOnLog(Player player, OperationType type, Timestamp time, OperationStatus status) {
        Operation operation = new Operation(type, time, player, status);
        log.add(operation);
    }

    /**
     * Метод предоставляет журнал действий, совершенных игроками в системе
     *
     * @return журнал действий игроков в системе
     */
    @Override
    public List<Operation> viewLog() {
        return getLog();
    }
}
