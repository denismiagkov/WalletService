package com.denismiagkov.walletservice.domain.model;

import java.util.Objects;

/**
 * Класс описывает игрока
 * */

public class Player {
    /**
     * Имя игрока
     * */
    private String firstName;

    /**
     * Фамилия игрока
     * */
    private String lastName;

    /**
     * Электронная почта игрока
     * */
    private String email;
    /**
     * Денежный счет
     * */
    private Account account;

    /**
     * Конструктор класса
     * */
    public Player(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Метод возвращает имя игрока
     * */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Метод возвращает фамилию игрока
     * */
    public String getLastName() {
        return lastName;
    }

    /**
     * Метод возвращает электроную почту игрока
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Метод возвращает счет игрока
     * */
    public Account getAccount() {
        return account;
    }

    /**
     * Метод устанвливает счет игрока
     * */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Метод toString()
     * */
    @Override
    public String toString() {
        return "Player{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     * Метод equals()
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(email, player.email);
    }

    /**
     * Метод hashcode()
     * */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
