package com.kang.kanglog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Slf4j
@Repository
public class LikeRepository {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public LikeRepository(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }

    public void mLike(Long postId, Long principalId) {
    }

    public void mUnLike(Long postId, Long principalId) {
    }
}
