package com.kang.kanglog.config.security;

import lombok.extern.slf4j.Slf4j;
import net.lunalabs.mj.utills.jwt.JwtProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class JwtVertifyFilter extends BasicAuthenticationFilter { //@Componet가 안 되어있기 때문에 직접 DI 가 안된다.


    private final HttpSession session;

    //ObjectMapper om = new ObjectMapper();

    public JwtVertifyFilter(AuthenticationManager authenticationManager,HttpSession session) {
        super(authenticationManager);
        this.session = session;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        log.info("권한이나 인증이 필요한 요청이 들어옴");

        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        log.info("Authorization는 " + header);


        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

//        String memberId = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
//                .getClaim("memberId").asString();

        log.info("token은 " + token);

        Long userId = null;

//        if (memberId != null) {
//            Member member = memberMapper.findByMemberUserId(memberId);
//
//            // 1. authenticationManager.authenticate() 함수를 타게 되면 인증을 한번 더 하게 되고
//            // 이때 비밀번호 검증을 하게 되는데, User 테이블에서 가져온 비밀번호 해시값으로 비교가 불가능하다.
//            // 2. 그래서 강제로 세션에 저장만 한다.
//            // 3. 단점은 @AuthenticationPrincipal 어노테이션을 사용하지 못하는 것이다.
//            // 4. 이유는 UserDetailsService를 타지 않으면 @AuthenticationPrincipal 이 만들어지지 않는다.
//            // 5. 그래서 @LoginUser 을 하나 만들려고 한다.
//            // 6. 그러므로 모든 곳에서 @AuthenticationPrincipal 사용을 금지한다. @LoginUser 사용 추천!!
//
//
//            PrincipalDetails principalDetails = new PrincipalDetails(member);
//            session.setAttribute("principal", principalDetails);
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            principalDetails,
//                            principalDetails.getPassword(),
//                            principalDetails.getAuthorities());
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            // JSESSIONID만료 시간이 -1 (브라우저가 닫힐 때까지)이 표시됩니다.
//        }

        chain.doFilter(request, response);
    }
}