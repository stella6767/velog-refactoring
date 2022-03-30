package com.kang.kanglog.web.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kang.kanglog.domain.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.*;
import java.util.List;

@Slf4j
public class PostResDto {


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Transactional(readOnly = true)
    public static class PostDto {

        private Long id;
        private String title;
        private String content;
        private String thumbnail;


        private User user;
        private List<Tag> tags;
        private List<Like> likes;
        private List<Comment> comments;
        private int likeCount;
        private boolean likeState;


        public PostDto(long principalId, Post post) {

            //https://stackoverflow.com/questions/17662634/failed-to-lazily-initialize-a-collection-when-inside-a-transaction
            log.info("트랜잭션 경계 안일텐데?? " + TransactionSynchronizationManager.isActualTransactionActive());


            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.thumbnail = post.getThumbnail();
            this.user = post.getUser();

            //log.info("이해가 안 되네? " + post.getTags());
            this.tags = post.getTags();
            this.likes = post.getLikes();

            //log.info("이해가 안 되네? " + post.getComments());
            this.comments = post.getComments();
            this.likeCount = this.likes.size(); //view에서 연산을 최소한 하기 위해
            /**
             * query 최적화를 한 번 해줘야 되긴 할 테지만, 귀찮으니까..걍
             */

            if(principalId != 0L){
                //좋아요 하트 색깔 로직
                post.getLikes().forEach((like)->{
                    if(like.getUser().getId() == principalId) {
                        this.likeState = true;
                    }
                });
            }
        }

        @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
        public PostResDto.PostDto PostDtoTest(Post post) {

            //https://stackoverflow.com/questions/17662634/failed-to-lazily-initialize-a-collection-when-inside-a-transaction
            log.info("트랜잭션 경계 안일텐데33?? " + TransactionSynchronizationManager.isActualTransactionActive());

            /**
             * 다른 영속성 컨텍스트 세션이라서?
             * 현재 영속성 컨텍스트의 상태를 확인할 수 있는 메서드가 없나>
             */


            PostResDto.PostDto build= PostResDto.PostDto.builder()
                    .comments(post.getComments())
                    .tags(post.getTags())
                    .build();

            return build;
        }


    }


}
