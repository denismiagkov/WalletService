package com.denismiagkov.walletservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность игрока")
public class PlayerDto {
    @Schema(description = "Имя")
    public String name;
    @Schema(description = "Фамилия")
    public String surname;
    @Schema(description = "Электронная почта")
    public String email;
    @Schema(description = "Логин")
    public String login;
    @Schema(description = "Пароль")
    public String password;

    public PlayerDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PlayerDto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
