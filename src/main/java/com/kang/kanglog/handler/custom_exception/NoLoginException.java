package com.kang.kanglog.handler.custom_exception;

public class NoLoginException extends RuntimeException{

    public NoLoginException(String message) {
        super(message);
    }
}
