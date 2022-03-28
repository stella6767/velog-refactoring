package com.kang.kanglog.domain;


import com.kang.kanglog.web.dto.user.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString(exclude = {"posts"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100) //12345 -> 해쉬(비밀번호 암호화)
    private String password;

    @Column
    private String email; //소셜가입이 아닌 사람은 이메일이 없도록..

    @Column  //이거 구현하기 귀찮아서 할지 말지 생각중..
    private String picture;

    @Transient
    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @OrderBy("id DESC")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //DTO 만들어서 LazyLoading issue 해결
    private List<Post> posts;


    public void commonJoin(String password, Role role){ //소셜 로그인 아닐시
        this.password = password;
        this.role = role;
    }

}