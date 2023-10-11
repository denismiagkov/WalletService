package com.denismiagkov.walletservice.infrastructure.in.menu;

import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.in.Option;
import com.denismiagkov.walletservice.infrastructure.in.commands.Finish;
import com.denismiagkov.walletservice.infrastructure.in.commands.OptionAuthorizePlayer;
import com.denismiagkov.walletservice.infrastructure.in.commands.OptionRegisterPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляет список доступных для пользователя команд, отображаемый в текущем меню
 */
public class Menu {

    /**
     * Список команд(директив), доступных для выбора пользователем в текущем меню в терминале
     */
    protected List<Option> list;

    /**
     * Конструктор класса
     * */
    public Menu(Console console) {
        this.list = new ArrayList<>();
    }

    /**
     * Метод выводит список доступных в текущем меню пользователю команд с указанием их порядкового номера.
     * Пользователь осуществит выбор команды путем ввода соответствующего числа (в частности, в терминал)
     *
     * @return список команд с указанием их идентификатора (порядкового номера)
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            /**
             * идентификатор (порядковый номер) команды
             * */
            sb.append(i + 1);
            sb.append(". ");
            /**
             * название (краткое описание) команды
             * */
            sb.append(list.get(i).getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Метод возвращает количество доступных в текущем меню команд
     *
     * @return число отображаемых в интерфейсе приложения команд
     */
    public int getSize() {
        return list.size();
    }


    /**
     * Метод запускает выполнение команды, выбранной пользователем
     *
     * @param choice идентификатор (порядковый номер) команды
     */
    public void execute(int choice) {
        list.get(choice - 1).execute();
    }

    /**
     * Метод запускает выполнение команды, выбранной пользователем,
     * принимая в качестве параметра логин и пароль игрока. Данная информация будет использована приложением
     * в процессе выполнения пользовательского запроса для поиска и обработки данных.
     *
     * @param choice   идентификатор (порядковый номер) команды
     * @param login    идентификатор (логин) игрока
     * @param password идентифицирующий признак (пароль) игрока
     */
    public void execute(int choice, String login, String password) {
        list.get(choice - 1).execute(login, password);
    }
}
