package com.kang.kanglog.web;

import com.kang.kanglog.config.anno.LoginCheck;
import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.domain.Comment;
import com.kang.kanglog.handler.custom_exception.NoLoginException;
import com.kang.kanglog.service.CommentService;
import com.kang.kanglog.utils.common.CMResDto;
import com.kang.kanglog.web.dto.comment.CommentReqDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kang.kanglog.web.dto.comment.CommentReqDto.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;


    @LoginCheck
    @DeleteMapping("/comment/{id}")
    public CMResDto<?> deleteById(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails details){
        log.info("댓글 삭제" + id);
        return  new CMResDto<>(commentService.삭제하기(id, details.getUser().getId()) ,"댓글 삭제", null);
    }

    @LoginCheck
    @PostMapping("/comment") //todo api 규격변경
    public CMResDto<?> save(@Valid @RequestBody CommentAddDto commentAddDto, @AuthenticationPrincipal PrincipalDetails principalDetails){   // content, imageId, userId(세션)

        return new CMResDto<>(1, "댓글 작성",commentService.댓글쓰기(principalDetails.getUser(), commentAddDto) );
    }

}
