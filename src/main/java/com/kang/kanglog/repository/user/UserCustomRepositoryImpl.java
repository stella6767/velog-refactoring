package com.kang.kanglog.repository.user;

import com.kang.kanglog.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.kang.kanglog.domain.QUser.*;

@Slf4j
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{

    /**
     *  spring data 엔티티 레파지토리 사용 안 할려고 했는데, 걍 귀찮아서 구현하겠다..
     */
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;


    public UserCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }

    @Override
    public User mfindByUsername(String username) {

        User userEntity = queryFactory
                .selectFrom(user)
                .where(
                        user.username.eq(username)
                        )
                .fetchOne();

        return userEntity;
    }


}
