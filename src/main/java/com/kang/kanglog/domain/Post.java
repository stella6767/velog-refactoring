package com.kang.kanglog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@ToString(exclude = {"user", "tags", "likes", "comments"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String title;

    private String content;

    private String thumbnail;

    @JsonIgnoreProperties({"posts"})
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post",  fetch = FetchType.LAZY) //mappedBy 하면 테이블의 칼럼 안 생김을 명시
    private List<Tag> tags = new ArrayList<>();

    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>(); //A 이미지에 홍길동, 장보고, 임꺽정 좋아요. (고소영)

    @OrderBy("id DESC")
    @JsonIgnoreProperties({"post"})
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Setter
    @Transient  //칼럼이 만들어지지 않는다.
    private int likeCount;

    @Setter
    @Transient
    private boolean likeState;


    public Post(Long id) {

        this.id = id;
    }

    public void add(User user, List<String> imgList){
        this.user = user;

        if (imgList.size() > 0) {
            //썸네일 추출
            this.thumbnail =imgList.get(0);
        }
    }


}