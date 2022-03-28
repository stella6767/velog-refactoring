package com.kang.kanglog.repository;

import com.kang.kanglog.domain.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;


@Slf4j
@Repository
public class CommentRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public CommentRepository(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }


    public Optional<Comment> findById(Long id) {

        return null;
    }

    public void deleteById(Long id) {


    }

    public Comment save(Comment comment) {


        return null;
    }
}
