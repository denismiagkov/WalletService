package com.denismiagkov.walletservice.repository.interfaces;

import com.denismiagkov.walletservice.domain.model.Operation;
import com.denismiagkov.walletservice.repository.OperationDAOImpl;

import java.util.List;

/**
 * Интерфейс объявляет методы, реализуемые репозиторием действия игрока в приложении
 */
public interface OperationDAO {

    /**
     * Метод сохраняет в базе данных сведения о действии, совершенном игроком в приложении
     *
     * @see OperationDAOImpl#saveOperation(Operation)
     */
    public void saveOperation(Operation operation);

    /**
     * Метод запрашивает в базе данных и возвращает список действий, совершенных всеми игроками в приложении
     *
     * @see OperationDAOImpl#saveOperation(Operation)
     */
    public List<Operation> getLog();
}
