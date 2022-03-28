package com.kang.kanglog.repository;

import com.kang.kanglog.domain.Comment;
import com.kang.kanglog.domain.QComment;
import com.kang.kanglog.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.kang.kanglog.domain.QComment.*;
import static com.kang.kanglog.domain.QUser.user;


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

        Optional<Comment> commentEntity = Optional.ofNullable(queryFactory
                .selectFrom(comment)
                        .fetchJoin()
                .where(
                        comment.id.eq(id)
                )
                .fetchOne());

        return commentEntity;
    }

    public void deleteById(Long id) {


    }

    public void save(Comment comment) {

        em.merge(comment);
    }
}
