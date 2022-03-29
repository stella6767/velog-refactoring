package com.kang.kanglog.repository.like;

import com.kang.kanglog.domain.Like;
import com.kang.kanglog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeCustomRepository {

    

}
