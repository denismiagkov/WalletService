package com.denismiagkov.walletservice.infrastructure.in.handlers;

import com.denismiagkov.walletservice.infrastructure.in.exceptions.IncorrectNameException;
import com.denismiagkov.walletservice.infrastructure.in.exceptions.IncorrectSurnameException;
import com.denismiagkov.walletservice.infrastructure.in.exceptions.InfoMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(RuntimeException exception) {
        InfoMessage data = new InfoMessage();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(ExpiredJwtException exception) {
        InfoMessage data = new InfoMessage();
        data.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(UnsupportedJwtException exception) {
        InfoMessage data = new InfoMessage();
        data.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(SignatureException exception) {
        InfoMessage data = new InfoMessage();
        data.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(MalformedJwtException exception) {
        InfoMessage data = new InfoMessage();
        data.setInfo("Authentication failed! " + exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }

}
