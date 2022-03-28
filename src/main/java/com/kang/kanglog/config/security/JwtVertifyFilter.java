package com.kang.kanglog.config.security;

import com.kang.kanglog.domain.User;
import com.kang.kanglog.repository.UserRepository;
import com.kang.kanglog.service.RedisService;
import com.kang.kanglog.utils.common.JwtProperties;
import com.kang.kanglog.utils.util_function.UtilManager;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class JwtVertifyFilter extends BasicAuthenticationFilter { //@Componet가 안 되어있기 때문에 직접 DI 가 안된다.

    private final HttpSession session;
    private final UserRepository userRepository;
    private final RedisService redisService;
    UtilManager utilManager = UtilManager.getInstance();

    public JwtVertifyFilter(AuthenticationManager authenticationManager, HttpSession session, UserRepository userRepository, RedisService redisService) {
        super(authenticationManager);
        this.session = session;
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        log.info("권한이나 인증이 필요한 요청이 들어옴");

        Cookie accessCookie = utilManager.getCookie(request, JwtProperties.ACCESS_TOKEN_NAME);

        Long userId = null;
        String accessToken = "";
        String refreshToken = "";
        String refreshUserId = "";

        try {

            if(accessCookie != null){
                log.info("accessCookie: "+accessCookie.toString());
                accessToken = accessCookie.getValue();
                userId = utilManager.getUserId(accessToken); //검증
                log.info("why.." + userId +" " +accessToken);
            }

            if( userId != null){
                User userEntity = userRepository.findById(userId).orElseThrow(()->{
                    return new IllegalArgumentException("id를 찾을 수 없습니다.");
                });

                //이중 처리..
                //principalDetailsService.loadUserByUsername(userEntity.getUsername());



                if(utilManager.validateToken(accessToken, userEntity)){
                    log.info("세션에 담고..");
                    PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

//                    HttpSession session = request.getSession();
//                    session.setAttribute("principal", principalDetails);
                }
            }
        }catch (ExpiredJwtException e){

            try {
                log.info("accessToken 기간이 만료되었다면");
                Cookie refresCookie = utilManager.getCookie(request,JwtProperties.REFRESH_TOKEN_NAME);
                if(refresCookie != null){
                    refreshToken = refresCookie.getValue();
                    log.info("refreshToken: " + refreshToken);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                response.sendError(-1, "예상치 못한 에러");
            }
        }

        try{
            if(StringUtils.hasText(refreshToken)){
                //재발급
                refreshUserId = redisService.getData(refreshToken);
                log.info("refreshUserId: " + refreshUserId);
                String claimId = (utilManager.getUserId(refreshToken)).toString();

                if(claimId == null || refreshUserId == null){
                    log.info("");
                    chain.doFilter(request, response);
                }

                if(refreshUserId.equals(claimId)) {
                    log.info("2차 검증!");
                    log.info("refresh검증"  + claimId);

                    User userEntity = userRepository.findById(Long.parseLong(refreshUserId)).orElseThrow(()->{
                        return new IllegalArgumentException("id를 찾을 수 없습니다.");
                    });

                    log.info("세션에 다시 담는다.");
                    //principalDetailsService.loadUserByUsername(userEntity.getUsername());

                    PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication); //세션에 다시 담고..

                    //세션에 직접 담아야 되나..
//                    HttpSession session = request.getSession();
//                    session.setAttribute("principal", principalDetails);


                    String newAccessToken =utilManager.generateAccessToken(Long.parseLong(refreshUserId));
                    Cookie newAccessCookie = utilManager.createCookie(JwtProperties.ACCESS_TOKEN_NAME, newAccessToken);

                    log.info("newToken: " + newAccessToken);

                    response.addCookie(newAccessCookie); //새로 발급한다.
                }
            }
        }catch(ExpiredJwtException e){
            //refreshToken조차 만료했다면..
            log.error(e.getMessage());
            //response.sendError(HttpStatus.UNAUTHORIZED.value(),"세션이 만료되었습니다. 다시 로그인하여주십시오.");
        }


        chain.doFilter(request, response);
    }
}