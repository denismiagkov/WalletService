package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информационное сообщение")
public class InfoMessage {
    private String info;

    public InfoMessage() {
        this.info = "";
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}

