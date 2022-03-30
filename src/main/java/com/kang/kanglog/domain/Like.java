package com.kang.kanglog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;



@ToString(exclude = {"post","user"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(
        name="likes",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames={"postId","userId"}
                )
        }
)
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.PRIVATE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @Setter(AccessLevel.PRIVATE)
    @JsonIgnoreProperties({"posts"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    public static Like createLike(Post postEntity, User principal) {

        Like like = new Like();
        like.setPost(postEntity);
        like.setUser(principal);

        return like;
    }

}