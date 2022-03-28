package com.kang.kanglog.config.security;

import com.kang.kanglog.service.RedisService;
import com.kang.kanglog.utils.util_function.Script;
import com.kang.kanglog.utils.common.CMResDto;
import com.kang.kanglog.utils.common.JwtProperties;
import com.kang.kanglog.utils.util_function.UtilManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private UtilManager utilManager = UtilManager.getInstance();

    private final RedisService redisService;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        Cookie refreshToken = utilManager.getCookie(request, JwtProperties.REFRESH_TOKEN_NAME);

        if (refreshToken != null){
            String refreshJwt = refreshToken.getValue();
            log.info("refreshToken: " + refreshJwt);

            if(refreshJwt != null){
                log.info("logout이 되면...");
                
                //redis 유저정보 삭제
                redisService.deleteData(refreshJwt);

                Cookie accessNullToken = utilManager.createNullCookie(JwtProperties.ACCESS_TOKEN_NAME);
                Cookie refreshNullToken = utilManager.createNullCookie(JwtProperties.REFRESH_TOKEN_NAME);

                response.addCookie(accessNullToken);
                response.addCookie(refreshNullToken);

                CMResDto<?> cmRespDto = new CMResDto(1,"로그아웃되었습니다.",null);

                Script.responseData(response, cmRespDto);
            }
        }


//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(null);
//        SecurityContextHolder.clearContext();
//        CMResDto<?> CMResDto = new CMResDto<>(1, "로그아웃되었습니다.", null);
//        Script.responseData(response, CMResDto);
    }


}