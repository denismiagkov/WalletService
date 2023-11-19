package com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.handlers;

import com.denismiagkov.walletservice.application.service.serviceImpl.exceptions.NotEnoughFundsOnAccountException;
import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.IncorrectInputNumberException;
import com.denismiagkov.walletservice.infrastructure.in.exception_hahdling.exceptions.InfoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    InfoMessage message;

    @Autowired
    public GlobalExceptionHandler(InfoMessage message) {
        this.message = message;
    }

    @ExceptionHandler(IncorrectInputNumberException.class)
    public ResponseEntity<InfoMessage> handleException(IncorrectInputNumberException exception) {
        message.setInfo(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughFundsOnAccountException.class)
    public ResponseEntity<InfoMessage> handleException(NotEnoughFundsOnAccountException exception) {
        message.setInfo(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(RuntimeException exception) {
        message.setInfo(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
