package com.kang.kanglog.web.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kang.kanglog.domain.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
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


    @Builder //원래는 안 쓰려 했는데..
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
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
            //https://localcoder.org/failed-to-lazily-initialize-a-collection-of-role
            //https://stackoverflow.com/questions/17662634/failed-to-lazily-initialize-a-collection-when-inside-a-transaction
            log.info("트랜잭션 경계 안일텐데 2 ?? " + TransactionSynchronizationManager.isActualTransactionActive());

            //왜 likes는 되고 나머지 tomany 연관관계는 안 되는거야..
            Hibernate.initialize(post.getComments());
            Hibernate.initialize(post.getTags());

            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.thumbnail = post.getThumbnail();
            this.user = post.getUser();
            //log.info("이해가 안 되네? 여기서 초기화를 해줘야 되는 이유가? " + post.getTags().size());
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






    }


}
