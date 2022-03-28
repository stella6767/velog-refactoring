package com.kang.kanglog.handler.custom_exception;

public class NoLoginException extends RuntimeException{

    public NoLoginException() {
        super("로그인이 필요한 서비스입니다.");
    }
}
