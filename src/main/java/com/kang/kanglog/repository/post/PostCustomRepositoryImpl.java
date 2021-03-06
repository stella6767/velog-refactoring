package com.kang.kanglog.repository.post;


import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.QLike;
import com.kang.kanglog.domain.QPost;
import com.kang.kanglog.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.kang.kanglog.domain.QLike.*;
import static com.kang.kanglog.domain.QPost.*;
import static com.kang.kanglog.domain.QUser.*;


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
    public Page<Post> mFindByKeyword(String keyword, Pageable pageable) {

        /**
         * 나중에 SearchCondation 사용해야 될 때 동적쿼리로 변경하도록 하자..
         * 그냥 사이드 프로젝트 하는 거니까 너무 정성을 쏟지는 말자.. 회사일도 있고.
         * 포폴로 제출할 때 감점요인이 되는 건 아니겠지?
         */

        List<Post> content = queryFactory
                .selectFrom(post)
                .where(
                        post.title.contains(keyword).or(post.content.contains(keyword))
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(post.id.desc())
                .fetch();


        List<Post> countQuery = queryFactory
                .selectFrom(post)
                .where(
                        post.title.contains(keyword).or(post.content.contains(keyword))
                )
                .orderBy(post.id.desc())
                .fetch();

        return new PageImpl<>(content, pageable, countQuery.size());
    }




    @Override
    public Page<Post> mfindAllByPage(Pageable pageable) {

        List<Post> content = queryFactory
                .selectFrom(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createDate.desc())
                .fetch();


        List<Post> countQuery = queryFactory
                .selectFrom(post)
                .orderBy(post.createDate.desc())
                .fetch();

        return new PageImpl<>(content, pageable, countQuery.size());
    }


}
