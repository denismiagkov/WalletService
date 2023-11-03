package com.denismiagkov.walletservice.infrastructure.in.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfoMessage {
    public String info;

    public InfoMessage(@Value("") String data) {
        this.info = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

