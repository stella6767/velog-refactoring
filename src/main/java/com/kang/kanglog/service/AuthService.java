package com.kang.kanglog.service;


import com.kang.kanglog.domain.User;
import com.kang.kanglog.repository.user.UserRepository;
import com.kang.kanglog.web.dto.user.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kang.kanglog.web.dto.user.UserReqDto.*;
import static com.kang.kanglog.web.dto.user.UserRespDto.*;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;


    @Transactional
    public String join(JoinDto joinDto) {

        //여기서 trim 검사말고 프론트쪽에서 검사하는 게 나을듯.
        User userEntity = userRepository.mfindByUsername(joinDto.getUsername());

        if(userEntity != null){
            throw new IllegalArgumentException("중복된 유저네임이 있습니다.");
        }


        String rawPassword = joinDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        User user = modelMapper.map(joinDto, User.class);
        user.commonJoin(encPassword, Role.USER);
        log.info("user: '{}'",user);

        userRepository.save(user);

        return "회원가입 성공";
    }


    public LoginRespDto loadUser(User user) {

        log.info("CSR 유저정보 유지" + user);
        LoginRespDto loginRespDto = new LoginRespDto();
        loginRespDto.toDto(user);

        return loginRespDto;
    }





//    @Transactional(readOnly = true)
//    public User googleProcess(Map<String, Object> data, HttpServletResponse response) {
//
//        /**
//         * 편의상 구글로그인만 진행..
//         */
//
//        OAuth2UserInfo googleUser =
//                new GoogleInfo((Map<String, Object>) data.get("profileObj"));
//
//        log.info("googleUser: " + googleUser);
//        User userEntity = userRepository.findByUsername("Google_" + googleUser.getId());
//
//        if (userEntity == null) {
//
//            UUID uuid = UUID.randomUUID();
//            String encPassword = new BCryptPasswordEncoder().encode(uuid.toString()); //아무거나 비번 만듬
//
//            User userRequest = User.builder()
//                    .username("Google_" + googleUser.getId())
//                    .password(encPassword)
//                    .email(googleUser.getEmail())
//                    .picture(googleUser.getImageUrl())
//                    .role(Role.USER)
//                    .build();
//
//            userEntity = userRepository.save(userRequest);
//        }
//
//
//        final String token = jwtService.generateAccessToken(userEntity.getId());
//        final String refreshJwt = jwtService.generateRefreshToken(userEntity.getId());
//
//        //cookie로 만듬
//        Cookie accessToken = utilManager.createCookie(JwtProperties.ACCESS_TOKEN_NAME, token);
//        Cookie refreshToken = utilManager.createCookie(JwtProperties.REFRESH_TOKEN_NAME, refreshJwt);
//
//        //RefreshToken을 Redis에 저장
//        jwtService.saveTokenInRedis(refreshJwt, userEntity.getId().toString());
//
//        log.info("refreshToken: " + refreshToken); //쿠키
//
//        //이제 이 쿠키를 클라이언트 서버로
//        response.addCookie(accessToken);
//        response.addCookie(refreshToken);
//
//
//        LoginRespDto loginRespDto = new LoginRespDto();
//        loginRespDto.toDto(userEntity);
//
//        return loginRespDto;
//    }



}

