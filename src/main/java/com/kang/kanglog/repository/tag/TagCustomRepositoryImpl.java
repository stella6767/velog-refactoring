package com.kang.kanglog.repository.tag;

import com.kang.kanglog.domain.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
public class TagCustomRepositoryImpl implements TagCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public TagCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }
    @Override
    public List<Tag> mFindByName(String name) {

        return null;
    }
    @Override
    public List<Tag> mFindUserTags(Long userId) {

        return null;
    }


}
