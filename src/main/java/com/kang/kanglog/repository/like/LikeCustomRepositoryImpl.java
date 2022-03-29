package com.kang.kanglog.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Slf4j
@Repository
public class LikeCustomRepositoryImpl implements LikeCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public LikeCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }

    @Override
    public void mLike(Long postId, Long principalId) {
    }
    @Override
    public void mUnLike(Long postId, Long principalId) {
    }
}
