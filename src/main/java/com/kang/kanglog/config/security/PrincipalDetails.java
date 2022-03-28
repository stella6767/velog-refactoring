package com.kang.kanglog.config.security;

import com.kang.kanglog.domain.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Data //principal (접근 주체) = 세션처럼 사용 = Spring Security Context 에 보관됨
public class PrincipalDetails implements UserDetails, OAuth2User {//UserDetails 상속받는 객체를 만든다.

    private User user;

    private Map<String,Object> attributes; //OAuth 제공자로부터 받은 회원 정보
    //왜 MAp으로 받냐면 OBject로 받으면 구글이면 구글, 카카오면 카카오, 따로따로 object를 다 만들어줘야 되는데
    //이렇게 받으면 모든 제공자로부터 키값으로 object를 받을 수 있음
    private boolean oAuth = false;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String,Object> attributes) {
        this.user = user;
        this.attributes = attributes;
        this.oAuth = true;  //어차피 attributes 들어오는 게 Oauth 로그인이라 true 설정
    }



    @Override
    public Map<String, Object> getAttributes() {
        // TODO Auto-generated method stub
        return attributes;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "몰라";
    }



    @Override// 계정의 비밀번호를 리턴한다.
    public String getPassword() {
        return user.getPassword();
    }

    @Override// 계정의 이름을 리턴한다.
    public String getUsername() {
        return user.getUsername();
    }

    @Override// 계정이 만료되지 않았는 지 리턴한다. (true: 만료안됨)
    public boolean isAccountNonExpired() {
        return true;   //안 쓸거면 그냥 true
    }

    @Override// 계정이 잠겨있지 않았는 지 리턴한다. (true: 잠기지 않음)
    public boolean isAccountNonLocked() { //예를 들어 아이디 3번 시도했는데 실패했으면 락인한다든가.. 그런 로직
        return true;
    }

    @Override// 비밀번호가 만료되지 않았는 지 리턴한다. (true: 만료안됨)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {// 계정이 활성화(사용가능)인 지 리턴한다. (true: 활성화)
        return true;
    }

    @Override// 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한개만)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Role 검증 하는 중");
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->"ROLE_"+user.getRole().toString());

        return collectors;
    }


}