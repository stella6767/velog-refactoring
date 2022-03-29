package com.kang.kanglog.repository.user;

import com.kang.kanglog.domain.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;


//@Configuration // 아 이거 곤란하네...
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

    

}
