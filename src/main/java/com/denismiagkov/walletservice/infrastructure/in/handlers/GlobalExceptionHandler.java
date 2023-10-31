package com.denismiagkov.walletservice.infrastructure.in.handlers;

import com.denismiagkov.walletservice.infrastructure.in.exceptions.IncorrectNameException;
import com.denismiagkov.walletservice.infrastructure.in.exceptions.IncorrectSurnameException;
import com.denismiagkov.walletservice.infrastructure.in.exceptions.InfoMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(RuntimeException exception){
        InfoMessage data = new InfoMessage();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
