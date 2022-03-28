package com.kang.kanglog.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserReqDto {


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoginDto {
        private String username;
        private String password;
    }

}
