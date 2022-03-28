package com.kang.kanglog.web.dto.post;

import com.kang.kanglog.domain.Post;
import lombok.Data;

public class PostReqDto {

    @Data
    public static class PostSaveReqDto {

        private String title;
        private String content;
        private String tags;
    }


}
