package com.kang.kanglog.repository.like;

public interface LikeCustomRepository {


    void mLike(Long postId, Long principalId);

    void mUnLike(Long postId, Long principalId);
}
