package com.kang.kanglog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString(exclude = {"post"})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;


    public void add(String temp, Post post){

        this.name = temp;
        this.post = post;

    }

}
