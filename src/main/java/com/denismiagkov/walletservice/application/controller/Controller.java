package com.denismiagkov.walletservice.application.controller;

import com.denismiagkov.walletservice.application.dto.AccountDto;
import com.denismiagkov.walletservice.application.dto.EntryDto;
import com.denismiagkov.walletservice.application.dto.PlayerDto;
import com.denismiagkov.walletservice.application.dto.TransactionDto;
import com.denismiagkov.walletservice.application.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import java.math.BigDecimal;
import java.util.List;

/**
 * Класс обрабатывает запросы, полученные от пользователя и управляет взаимодействием между внешним
 * и внутренними слоями приложения
 */
@org.springframework.stereotype.Controller
public class Controller {
    /**
     * Cервис приложения
     */
    @Autowired
    private Service service;

    /**
     * Конструктор класса
     */
  //  @Autowired
    public Controller(Service service) {
        System.out.println("Controller created!");
        this.service = service;
    }


    @RequestMapping("/")
    public String startPage(){
        return "start-view";
    }


    @RequestMapping("/registerForm")
    public String getInfoForRegistration(Model model){
        PlayerDto playerDto = new PlayerDto();
        model.addAttribute("playerDto", playerDto);
        return "register-form-view";
    }

    @RequestMapping("/loginForm")
    public String getInfoForLogin(Model model){
        EntryDto entryDto = new EntryDto();
        model.addAttribute("entryDto", entryDto);
        return "login-form-view";
    }


    /**
     * Метод вызывает в сервисе метод регистрации нового игрока. В зависимости от полученного результата
     * возвращает в консоль булевое значение.
     *
     * @param playerDto ДТО игрока
     * @return статус успеха регистрации
     */
    @RequestMapping("/registration")
    public String registerPlayer(@ModelAttribute("playerDto") PlayerDto playerDto) throws RuntimeException {
        System.out.println(playerDto);
        service.registerPlayer(playerDto);
        return "registration-view";
    }

    /**
     * Метод вызывает в сервисе метод аутентентификации пользователя.     *
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     */
    @RequestMapping("/authentication")
    public boolean authorizePlayer(@ModelAttribute("entryDto") EntryDto entryDto) throws RuntimeException {
        System.out.println(entryDto);
        return service.authorizePlayer(entryDto);
    }

    /**
     * Метод передает в сервис запрос о текущем состоянии баланса игрока.
     *
     * @param login идентификатор игрока (логин)
     */
    public AccountDto getCurrentBalance(String login) {
        return service.getCurrentBalance(login);
    }

    /**
     * Метод вызывает в сервисе историю дебетовых и кредитных операций по счету игрока.
     *
     * @param login идентификатор игрока (логин)
     */
    public List<TransactionDto> getTransactionsHistory(String login) {
        return service.getTransactionHistory(login);
    }

    /**
     * Метод вызывает метод сервиса по пополнению денежного счета игрока.
     *
     * @param login  идентификатор игрока (логин)
     * @param amount сумма выполняемой операции
     */
    public boolean topUpAccount(String login, BigDecimal amount) {
        try {
            service.topUpAccount(login, amount);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Метод вызывает метод сервиса по списанию денежных средств со счета игрока.
     *
     * @param login  идентификатор игрока (логин)
     * @param amount сумма выполняемой операции
     */
    public void writeOffFunds(String login, BigDecimal amount) throws RuntimeException {
        service.writeOffFunds(login, amount);
    }

    /**
     * Метод вызывает в сервисе метод по фиксации в журнале аудита действия игрока по выходу из приложения.
     *
     * @param login    идентификатор игрока (логин)
     * @param password идентифицирующий признак игрока (пароль)
     */
    public void logExit(String login, String password) {
        service.logExit(login, password);
    }

}

