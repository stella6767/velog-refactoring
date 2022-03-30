package com.kang.kanglog.web;

import com.kang.kanglog.config.anno.LoginCheck;
import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.service.LikesService;
import com.kang.kanglog.utils.common.CMResDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private static final Logger log = LoggerFactory.getLogger(LikeController.class);
    private final LikesService likesService;

    @LoginCheck
    @PostMapping("/like/{postId}")
    public CMResDto<?> like(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long postId){

        return new CMResDto<>(1, "좋아요 click", likesService.좋아요(postId, principalDetails.getUser().getId()));
    }


    @LoginCheck
    @DeleteMapping("/like/{postId}")
    public CMResDto<?> unLike(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long postId){

        return new CMResDto<>(1, "좋아요 해제 ", likesService.싫어요(postId, principalDetails.getUser().getId()));
    }



}
