package com.kang.kanglog.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotNull;

public class CommentReqDto {

    @Data
    public static class CommentAddDto {

        @NotNull(message = "postId를 입력해주세요.")
        private Long postId;

        private String content;
    }



}
