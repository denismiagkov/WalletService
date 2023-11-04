package com.denismiagkov.walletservice.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PlayerDto {
    @Pattern(regexp = "[a-zA-Zа-яА-Я]+", message = "The field may contain only letters!")
    public String name;
    @Pattern(regexp = "[a-zA-Zа-яА-Я]+", message = "The field may contain only letters!")
    public String surname;
    @Pattern(regexp = "\\w+@\\w+\\.(com|ru)", message = "Invalid email!")
    public String email;
    @NotBlank (message = "The field mightn't be empty")
    public String login;
    @Size(min = 6, message = "Name must be minimum 2 symbols!")
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
