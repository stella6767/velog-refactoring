package com.kang.kanglog.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@Repository
public class PostRepository {


    private final JPAQueryFactory queryFactory;

    private final EntityManager em;


    public PostRepository(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }




}
