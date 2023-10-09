package com.denismiagkov.walletservice.domain.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Operation {
    private OperationType type;
    private Timestamp time;
    private Player player;
    private OperationStatus status;

    public Operation(OperationType type, Timestamp time, Player player, OperationStatus status) {
        this.type = type;
        this.time = time;
        this.player = player;
        this.status = status;
    }
//
//    public Player getPlayer() {
//        return player;
//    }
//
//    public void setPlayer(Player player) {
//        this.player = player;
//    }

    @Override
    public String toString() {
        return "Operation{" +
                "type='" + type + '\'' +
                ", time=" + time +
                ", player='" + player + " " + //player.getLastName() + '\'' +
                ", status=" + status +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return type == operation.type && Objects.equals(time, operation.time) && Objects.equals(player, operation.player) && status == operation.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, time, player, status);
    }
}
