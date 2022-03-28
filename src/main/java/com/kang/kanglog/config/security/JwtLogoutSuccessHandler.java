package com.kang.kanglog.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lunalabs.mj.utills.common.CMResDto;
import net.lunalabs.mj.utills.common.Script;
import net.lunalabs.mj.utills.jwt.JwtProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {



    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("로그아웃 성공");
        String id = request.getParameter("id");
        log.info("id=>" + id);
        //String token = request.getHeader(JwtProperties.HEADER_STRING);
        String token = request.getHeader(JwtProperties.HEADER_STRING);
        log.info("한 번 발급된 토큰을 강제로 만료시킬 방법이 없나.." + token);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();


        CMResDto<?> cmRespDto = new CMResDto<>(1, "로그아웃되었습니다.", null);
        Script.responseData(response, cmRespDto);
    }


}