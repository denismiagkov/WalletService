package com.denismiagkov.auditstarter.auditdao;

import com.denismiagkov.auditstarter.operation.Operation;

import java.util.List;

/**
 * Интерфейс объявляет методы, реализуемые репозиторием действия игрока в приложении
 */
public interface AuditDAO {

    /**
     * Метод сохраняет в базе данных сведения о действии, совершенном игроком в приложении
     */
    void saveOperation(Operation operation);

    /**
     * Метод запрашивает в базе данных и возвращает список действий, совершенных всеми игроками в приложении
     */
    List<Operation> getLog();
}
