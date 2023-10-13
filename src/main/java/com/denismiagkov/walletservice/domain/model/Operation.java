package com.denismiagkov.walletservice.domain.model;

import java.sql.Timestamp;
import java.util.Objects;
import com.denismiagkov.walletservice.domain.model.OperationType;

/**
 * Класс описывает действие игрока в системе. Служит для целей аудита
 * */
public class Operation {
    /**
     * Тип действия
     * @see OperationType
     * */
    private  OperationType type;
    /**
     * Дата и время совершения действия
     * */
    private Timestamp time;
    /**
     * Игрок, выполнивший действие
     * */
    private com.denismiagkov.walletservice.domain.model.Player player;
    /***
     * Статус успеха действия {@link OperationStatus}
     * */
    private com.denismiagkov.walletservice.domain.model.OperationStatus status;

    /**
     * Конструктор класса
     * */
    public Operation(OperationType type, Timestamp time, Player player, OperationStatus status) {
        this.type = type;
        this.time = time;
        this.player = player;
        this.status = status;
    }

    /**
     * Метод возвращает тип совершенного действия
     * */
    public OperationType getType() {
        return type;
    }

    /**
     * Метод toString()
     * */
    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", time=" + time +
                ", player='" + player + " " + //player.getLastName() + '\'' +
                ", status=" + status +
                '}' + "\n";
    }

    /**
     * Метод equals()
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return type == operation.type && Objects.equals(time, operation.time) && Objects.equals(player, operation.player) && status == operation.status;
    }

    /**
     * Метод hashcode()
     * */
    @Override
    public int hashCode() {
        return Objects.hash(type, time, player, status);
    }
}
