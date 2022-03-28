package com.kang.kanglog.repository;


import com.kang.kanglog.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@Repository
public class PostRepository {


    private final JPAQueryFactory queryFactory;

    private final EntityManager em;


    public PostRepository(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }


    public Page<Post> mLikeList(Pageable pageable, Long principalId) {

        return null;
    }

    public Page<Post> mFindByKeyword(String keyword, Pageable pageable) {

        return null;
    }

    public Optional<Post> findById(Long id) {

        return null;
    }

    public void deleteById(Long id) {


    }

    public Page<Post> mTrending(Pageable pageable) {

        return null;
    }

    public Page<Post> findAll(Pageable pageable) {

        return null;
    }
}
