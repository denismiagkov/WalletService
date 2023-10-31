package com.denismiagkov.walletservice.application.service.serviceImpl;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.domain.model.OperationStatus;
import com.denismiagkov.walletservice.domain.model.OperationType;
import com.denismiagkov.walletservice.domain.service.OperationService;
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
public class OperationServiceImpl implements OperationService {

    /**
     * ДАО операции (действия игрока в приложении)
     */
    OperationDAOImpl operationDAO;

    /**
     * Конструктор класса
     */
    @Autowired
    public OperationServiceImpl() {
        this.operationDAO = new OperationDAOImpl();
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
        operationDAO.saveOperation(operation);
    }

    /**
     * Метод предоставляет журнал действий, совершенных игроками в системе
     *
     * @return журнал действий игроков в системе
     */
    @Override
    public List<Operation> viewLog() {
        return operationDAO.getLog();
    }
}
