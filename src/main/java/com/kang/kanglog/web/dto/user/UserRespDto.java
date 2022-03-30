package com.kang.kanglog.web.dto.user;

import com.kang.kanglog.domain.Tag;
import com.kang.kanglog.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserRespDto {


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class UserVelogRespDto {
        private User user;
        private Long postCount;
        private List<Tag> tags;

    }



    @NoArgsConstructor
    @Data
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String email;
        private String picture;


        public void toDto(User principalDetails) {
            this.id = principalDetails.getId();
            this.picture = principalDetails.getPicture();
            this.email = principalDetails.getEmail();
            this.username = principalDetails.getUsername();

        }

    }
}
