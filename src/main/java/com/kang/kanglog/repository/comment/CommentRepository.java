package com.kang.kanglog.repository.comment;

import com.kang.kanglog.domain.Comment;
import com.kang.kanglog.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    

}
