package com.kang.kanglog.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kang.kanglog.repository.user.UserRepository;
import com.kang.kanglog.service.RedisService;
import com.kang.kanglog.utils.util_function.Script;
import com.kang.kanglog.utils.common.CMResDto;
import com.kang.kanglog.utils.util_function.UtilManager;
import com.kang.kanglog.web.dto.user.UserReqDto.LoginDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//토큰 만들어주기

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    //formlogin을 disable했기 때문에 꼭 이 필터를 수정해서 등록시켜줘야함

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper om;
    private final UserRepository userRepository;

    private final RedisService redisService;


    UtilManager utilManager = UtilManager.getInstance();


    public JwtLoginFilter(AuthenticationManager authenticationManager, ObjectMapper om, UserRepository userRepository, RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.om = om;
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    // 주소: Post 요청으로 /login 요청
    @Override   //기존 로그인 방식을 갈아치우는 과정
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.info("로그인 요청 옴");

        //todo request url 로 USER와 OPorator 를 분기하자.

        LoginDto loginDto = null;

        try {
            loginDto = om.readValue(request.getInputStream(), LoginDto.class); //json => java object
            log.info("로그인 dto: '{}'", loginDto);
        } catch (Exception e) {
            log.warn("JwtLoginFilter : 로그인 요청 dto 생성 중 실패: '{}'", e.getMessage());
            //e.printStackTrace();
        }


        //1. UsernamePassword 토큰 만들기
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        //2. AuthenticationManager에게 토큰을 전달하면 -> 자동으로 UserDetailsService가 호출=> 응답 Authentication
        Authentication authentication = authenticationManager.authenticate(authToken);


        return authentication;
        //return 될 때 authentication객체가 session 영역에 저장됨.
        //굳이 세션을 만들 이유는 없지만, 권한 처리 때문에 session에 넣어주자.
    }

    @Override //JWT 토큰 만들어서 응답
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        //principalDetails 객체를 받아오고
        log.info("로그인 완료되어서 세션 만들어짐. 이제 JWT토큰 만들어서 response.header에 응답할 차리");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        utilManager.generateLoginCookie(response, principalDetails, redisService);

        Script.responseData(response, new CMResDto(1, "로그인 성공", principalDetails.getUser()), om);
    }

}