package com.denismiagkov.walletservice.infrastructure.in.handlers;

import com.denismiagkov.walletservice.infrastructure.in.exceptions.InfoMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    InfoMessage message;

    @Autowired
    public GlobalExceptionHandler(InfoMessage infoMessage) {
        this.message = infoMessage;
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(RuntimeException exception) {
        message.setInfo(exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(ExpiredJwtException exception) {
        message.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(UnsupportedJwtException exception) {
        message.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(SignatureException exception) {
        message.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(MalformedJwtException exception) {
        message.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}
