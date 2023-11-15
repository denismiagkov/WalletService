package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;

@Schema(description = "Информационное сообщение")
@Component
public class InfoMessage {
    private String info;

    public InfoMessage() {
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}

