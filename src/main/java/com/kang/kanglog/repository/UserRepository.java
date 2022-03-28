package com.kang.kanglog.repository;

import com.kang.kanglog.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@Repository
public class UserRepository {


    private final JPAQueryFactory queryFactory;

    private final EntityManager em;


    public UserRepository(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }


    public User findByUsername(String username) {

        return null;
    }

    public User save(User user) {

        return null;
    }
}
