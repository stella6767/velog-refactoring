package com.kang.kanglog.repository;

import com.kang.kanglog.domain.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class TagRepository {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public TagRepository(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }

    public List<Tag> mFindByName(String name) {

        return null;
    }

    public List<Tag> mFindUserTags(Long userId) {

        return null;
    }
}
