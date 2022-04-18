package com.kang.kanglog.web;


import com.kang.kanglog.config.anno.LoginCheck;
import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.User;
import com.kang.kanglog.handler.custom_exception.NoLoginException;
import com.kang.kanglog.service.UserService;
import com.kang.kanglog.utils.common.CMResDto;
import com.kang.kanglog.web.dto.user.UserRespDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static com.kang.kanglog.web.dto.user.UserRespDto.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    @GetMapping("/user/{userId}") //개인 벨로그
    public CMResDto<?> profile(@PathVariable Long userId) {

        return new CMResDto<>(1, "개인벨로그",userService.회원블로그(userId));
    }


    @LoginCheck
    @GetMapping("/user/likelist")  //내가 좋아요 한 글들.
    public CMResDto<?> findAllByLike(@AuthenticationPrincipal PrincipalDetails details, @PageableDefault(size = 10) Pageable pageable){

        return new CMResDto<>(1, "좋아요 한 게시글 목록", userService.좋아요게시글목록(details.getUser().getId(), pageable));
    }

    @LoginCheck
    @DeleteMapping("/user") //미구현
    public CMResDto<?> deleteById(@AuthenticationPrincipal PrincipalDetails details){

        return new CMResDto<>(userService.회원탈퇴(details.getUser().getId()), "회원 탈퇴", null);
    }


    @LoginCheck
    @PutMapping("/user/{id}/profileImageUrl")
    public CMResDto<?> profileImageUrlUpdate(@PathVariable Long id, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request){

        log.info("파일 받기 : "+profileImageFile.getOriginalFilename());

        //todo 리팩토링 대상
        User userEntity = userService.회원사진변경(profileImageFile, principalDetails, request);
        principalDetails.setUser(userEntity); //세션 값에 저장된 imagProfile도 변경함으로서 세션 이미지를 들고있는 경로 모두 변경

        return new CMResDto<>(1, "프로필 사진을 변경",  userEntity.getProfileImgUrl());
    }




}
