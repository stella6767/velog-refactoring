package com.kang.kanglog.repository.post;


import com.kang.kanglog.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


/**
 * Impl 이 강제성이 있는 규칙이었나? 어이가 없네..
 */

@Slf4j
@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {


    private final JPAQueryFactory queryFactory;
    private final EntityManager em;


    public PostCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }

    @Override
    public Page<Post> mLikeList(Pageable pageable, Long principalId) {

        return null;
    }

    @Override
    public Page<Post> mFindByKeyword(String keyword, Pageable pageable) {

        return null;
    }

    @Override
    public Page<Post> mTrending(Pageable pageable) {

        return null;
    }
    @Override
    public Page<Post> mfindAllByPage(Pageable pageable) {

        return null;
    }


}
