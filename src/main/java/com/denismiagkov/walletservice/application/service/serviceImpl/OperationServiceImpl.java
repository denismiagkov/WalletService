package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.auditstarter.auditservice.AuditService;
import com.denismiagkov.auditstarter.auditservice.AuditServiceImpl;
import com.denismiagkov.auditstarter.operation.Operation;
import com.denismiagkov.auditstarter.operation.OperationStatus;
import com.denismiagkov.auditstarter.operation.OperationType;
import com.denismiagkov.walletservice.repository.OperationDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Низкоуровневый сервис, реализующий методы <strong>для реализации аудита действий игрока</strong>.
 * Перечень фиксируемых действий устанавливается в классе {@link OperationType}
 * Описанные в классе методы вызываются высокоуровневым сервисом для выполнения конкретных специализированных
 * операций, соответствующих бизнес-логике.
 */
@Service
public class OperationServiceImpl implements AuditService {

    AuditService auditService;

    /**
     * ДАО операции (действия игрока в приложении)
     */
    OperationDAOImpl operationDAO;


    /**
     * Конструктор класса
     */
    @Autowired
    public OperationServiceImpl(OperationDAOImpl operationDAO) {
        this.operationDAO = operationDAO;
        this.auditService = new AuditServiceImpl(this.operationDAO);
    }

    /**
     * Метод записывает в журнал атрибуты действия, совершенного игроком.
     *
     * @param playerId идентификатор игрока, совершившего действие
     * @param type     вид совершенного действия (из перечня {@link OperationType}
     * @param time     время совершения действия
     * @param status   успех/неуспех совершенного действия
     */
    @Override
    public void putOnLog(int playerId, OperationType type, Timestamp time, OperationStatus status) {
        Operation operation = new Operation(type, time, status, playerId);
        auditService.putOnLog(playerId, type, time, status);
    }

    /**
     * Метод предоставляет журнал действий, совершенных игроками в системе
     *
     * @return журнал действий игроков в системе
     */
    @Override
    public List<Operation> viewLog() {
        return auditService.viewLog();
    }
}
