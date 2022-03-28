package com.kang.kanglog.web;


import com.kang.kanglog.config.anno.LoginCheck;
import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.service.AuthService;
import com.kang.kanglog.utils.common.CMResDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.kang.kanglog.web.dto.user.UserReqDto.*;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;



    @PostMapping("/auth/join") //Oauth 가 아닌
    public CMResDto<?> join(@Valid @RequestBody JoinDto joinDto) {
        log.info("회원가입 요청 들어옴 " + joinDto);//@RequestBody가 받을 수 있는 application/json 설정을 프론트엔드 쪽에서 꼭 해주라ㅠㅠ

        //이미 존재하는 닉네임 상관없이..
        //utils 함수로 프론트서버로 자바스크립트 함수를 던져주는 것보다는 이게 그냥 제이슨 객체를 던지는 게 나을듯.
        return new CMResDto(HttpStatus.OK.value(), authService.join(joinDto), null);
    }


    @LoginCheck
    @GetMapping("/auth/loadUser")
    public CMResDto<?> loadUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return new CMResDto(HttpStatus.OK.value(), "CSR 로그인 정보 유지", authService.loadUser(principalDetails.getUser()));
    }


//    //소셜 로그인
//    @PostMapping("/auth/oauthLogin")
//    public CMResDto<?> oauthLogin(@RequestBody Map<String, Object> data, HttpServletResponse response) {
//        log.info("소셜 로그인 진행: " + data);
//
//        User userEntity = getUserEntity(data, response);
//
//        //return new CMResDto<>(1,"로그인성공", jwtService.makeLoginRespDto(userEntity));
//        return new CMResDto(1, "로그인성공", jwtService.makeLoginRespDto(userEntity));
//    }


}

