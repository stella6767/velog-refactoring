package com.kang.kanglog.repository.like;

import com.kang.kanglog.domain.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeCustomRepository {


    Page<Like> mLikeList(Pageable pageable, Long principalId);

    long mUnLike(Long postId, Long principalId);
}
