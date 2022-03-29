package com.kang.kanglog.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kang.kanglog.config.security.*;
import com.kang.kanglog.repository.user.UserRepository;
import com.kang.kanglog.service.RedisService;
import com.kang.kanglog.utils.common.JwtProperties;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@EnableWebSecurity //시큐리티 활성화
@Configuration // ioc 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final HttpSession session;
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
    private final ObjectMapper om;
    private final RedisService redisService;




    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        //web.ignoring().mvcMatchers("/**"); //문지기가 사람들을 다 통과시켜줌
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())//@CrossOrigin(인증X), 시큐리티 필터에 등록 인증(O)
                .and()
                .csrf().disable() //rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// STATELESS: session을 사용하지 않겠다는 의미
                .and()
                .addFilter(jwtLoginFilter())
                .addFilter(new JwtVertifyFilter(authenticationManager(), session, userRepository, redisService))
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtAccessDeniedHandler()) //권한이 없을 때
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated() //인증만 되면 ok
                .antMatchers("/admin/**").access("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(jwtLogoutSuccessHandler)
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(oAuth2DetailsService)

        ;
    }




    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //내서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지를 설정
        config.addAllowedOriginPattern("*"); //모든 ip에 응답을 허용하겠다.
        config.addAllowedMethod("*"); // 모든 post,get,put,delete, patch ..요청을 허용하겠다.
        config.addAllowedHeader("*"); //모든 header에 응답을 허용하겠다.
        //config.addAllowedOrigin("*");
        config.addExposedHeader(JwtProperties.HEADER_STRING); //authorization 노출
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }




    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        JwtAccessDeniedHandler jwtAccessDeniedHandler = new JwtAccessDeniedHandler();

        return jwtAccessDeniedHandler;
    }


    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

        return jwtAuthenticationEntryPoint;
    }


    @Bean
    public JwtLoginFilter jwtLoginFilter() throws Exception {
        final JwtLoginFilter jwtAuthenticationFilter = new JwtLoginFilter(authenticationManager(), om, userRepository, redisService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
        //왜 2번이나 설정해줘야 되는지 모르겠다..


        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new CustomSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new CustomFailureHandler());

        return jwtAuthenticationFilter;
    }


    //@Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encode());
        return authProvider;
    }




}
