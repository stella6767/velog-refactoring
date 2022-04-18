package com.kang.kanglog.handler;

import com.kang.kanglog.handler.custom_exception.NoLoginException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);




    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(Exception e){

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); //에러를 보낸다.
    }

    @ExceptionHandler(value= NoLoginException.class)
    public ResponseEntity<?> noLoginException(NoLoginException e, HttpServletResponse response) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED); //에러를 보낸다.
    }



}