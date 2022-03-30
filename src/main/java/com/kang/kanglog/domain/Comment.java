package com.kang.kanglog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;


@ToString(exclude = {"post","user"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300, nullable = false)
    private String content;

    @JsonIgnore
    @JoinColumn(name = "postId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JsonIgnoreProperties({"posts"})
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    public void add(Post post, User user){
        this.post = post;
        this.user = user;
    }



}