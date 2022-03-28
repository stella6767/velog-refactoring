package com.kang.kanglog.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class UserReqDto {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginDto {

        private String username;
        private String password;
    }


    @Data
    public static class JoinDto {
        
        @NotNull(message = "username을 입력해주세요")
        private String username;
        @NotNull(message = "password를 입력해주세요")
        private String password;
    }


    @Data
    public static class userImageReqDto {

        private MultipartFile file;
        private String caption;
        private String tags;

    }

}
