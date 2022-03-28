package com.kang.kanglog.web.dto.comment;

import lombok.Data;

public class CommentReqDto {

    @Data
    public static class CommentAddDto {
        private Long postId;
        private String content;
    }



}
