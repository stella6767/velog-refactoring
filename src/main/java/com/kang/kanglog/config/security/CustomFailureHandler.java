package com.kang.kanglog.config.security;

import com.kang.kanglog.utils.util_function.Script;
import com.kang.kanglog.utils.common.CMResDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        log.info("로그인 실패..");
        CMResDto<?> CMResDto = new CMResDto(0, "로그인 실패", null);

        Script.responseData(httpServletResponse, CMResDto);


    }
}
