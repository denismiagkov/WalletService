package com.denismiagkov.walletservice.infrastructure.in;

import com.denismiagkov.walletservice.application.Controller;
import com.denismiagkov.walletservice.infrastructure.in.menu.MainMenu;
import com.denismiagkov.walletservice.infrastructure.in.menu.ProfileMenu;

import java.math.BigDecimal;
import java.util.Scanner;

public class Console implements View {
    private Controller controller;
    private Scanner scanner;
    private boolean work;
    private MainMenu mainMenu;
    private ProfileMenu profileMenu;


    public Console(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
        this.work = true;
        this.mainMenu = new MainMenu(this);
        this.profileMenu = new ProfileMenu(this);
    }

    @Override
    public void print(String text) {
        System.out.println(text);

    }

    @Override
    public void start() {
        while (work) {
            System.out.println("Выберите действие: ");
            System.out.println(mainMenu.print());
            String choice = scanner.nextLine();
            if (check(choice, mainMenu.getSize())) {
                mainMenu.execute(Integer.parseInt(choice));
            } else {
                fail();
            }
        }
    }

    @Override
    public void startProfile(String login, String password) {
        while (work) {
            System.out.println("Выберите действие: ");
            System.out.println(profileMenu.print());
            String choice = scanner.nextLine();
            if (check(choice, profileMenu.getSize())) {
                profileMenu.execute(Integer.parseInt(choice), login, password);
            } else {
                fail();
            }
        }
    }

    private boolean check(String text, int n) {
        return text.matches("[0-9]+") && Integer.parseInt(text) <= n && Integer.parseInt(text) > 0;
    }

    public void fail() {
        System.out.println("Ошибка ввода!");
    }

    public void finish(String login, String password) {
        System.out.println("Работа программы завершена.");
        controller.logExit(login, password);
        work = false;
        scanner.close();
    }

    public void getDataToRegisterPlayer() {
        String[] input = new String[5];
        System.out.println("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.println("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.println("Введите адрес электронной почты: ");
        String email = scanner.nextLine();
        System.out.println("Введите логин: ");
        String login = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        controller.registerPlayer(firstName, lastName, email, login, password);
        System.out.println("Поздравляю! Вы успешно зарегистрированы!");
    }

    public void getDataToAuthorizePlayer() {
        System.out.println("Введите логин: ");
        String login = scanner.nextLine();
        System.out.println("Введите пароль: ");
        String password = scanner.nextLine();
        if (controller.authorizePlayer(login, password)) {
            startProfile(login, password);
        }
    }

    public void showCurrentBalance(String login, String password) {
        BigDecimal balance = controller.getCurrentBalance(login, password);
        System.out.println("Ваш текущий баланс составляет: " + balance + " денежных единиц");
    }

    public void callTopUpAccount(String login, String password) {
        System.out.println("Введите уникальный идентификатор длиной 10 символов");
        String uniqueId = scanner.nextLine();
        System.out.println("Введите сумму пополнения: ");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));
        boolean success = controller.topUpAccount(login, password, uniqueId, amount);
        if (success) {
            System.out.println("Ваш счет пополнен на сумму: " + amount + " денежных единиц\n");
        }
    }

    public void callWriteOffFunds(String login, String password) {
        System.out.println("Введите уникальный идентификатор длиной 10 символов");
        String uniqueId = scanner.nextLine();
        System.out.println("Введите сумму списания: ");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));
        boolean success = controller.writeOffFunds(login, password, uniqueId, amount);
        if (success) {
            System.out.println("С вашего счета списано: " + amount + " денежных единиц\n");
        }
    }

    public void showTransactionHistory(String login, String password) {
        String transactionHistory = controller.getTransactionsHistory(login, password);
        System.out.println("История транзакций по Вашему счету: " + transactionHistory);
    }
}
