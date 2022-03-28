package com.kang.kanglog.utils.common;

public interface JwtProperties {

    /**
     * 나중에 config 서버로 옮기던가 해야것다.
     */

    String SECRET_KEY = "kimdonggeunkimdonggeunkimdonggeunkimdonggeunkimdonggeunkimdonggeunkimdonggeunkimdonggeun"; // 우리 서버만 알고 있는 비밀값
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

    int ACCESS_TOKEN_VALIDATION_SECOND = 1000*60*10; //10분
    int REFRESH_TOKEN_VALIDATION_SECOND = 1000*60*60*24*7; //1주

    String ACCESS_TOKEN_NAME = "accessToken";
    String REFRESH_TOKEN_NAME = "refreshToken";

}
