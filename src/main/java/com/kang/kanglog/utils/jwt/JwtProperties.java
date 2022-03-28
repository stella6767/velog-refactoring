package com.kang.kanglog.utils.jwt;

public interface JwtProperties {

    String SECRET = "강민규"; // 우리 서버만 알고 있는 비밀값
    Integer EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
