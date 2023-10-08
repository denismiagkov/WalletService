package com.denismiagkov.walletservice.infrastructure.in.menu;

import com.denismiagkov.walletservice.infrastructure.in.Console;
import com.denismiagkov.walletservice.infrastructure.in.Option;
import com.denismiagkov.walletservice.infrastructure.in.commands.Finish;
import com.denismiagkov.walletservice.infrastructure.in.commands.OptionAuthorizePlayer;
import com.denismiagkov.walletservice.infrastructure.in.commands.OptionRegisterPlayer;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    protected List<Option> list;

    public Menu(Console console) {
        this.list = new ArrayList<>();
    }

    public String print(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(i+1);
            sb.append(". ");
            sb.append(list.get(i).getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getSize(){
        return list.size();
    }

    public void execute(int choice){
        list.get(choice-1).execute();
    }
    public void execute(int choice, String login, String password){
        list.get(choice-1).execute(login, password);
    }
}
