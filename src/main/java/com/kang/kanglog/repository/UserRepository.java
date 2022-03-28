package com.kang.kanglog.repository;

import com.kang.kanglog.domain.QUser;
import com.kang.kanglog.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.kang.kanglog.domain.QUser.*;

@Slf4j
@Repository
public class UserRepository {

    /**
     * 굳이 인터페이스를 안 만들겠다.. spring data 엔티티 레파지토리도 사용하지 않겠다.
     */


    private final JPAQueryFactory queryFactory;

    private final EntityManager em;


    public UserRepository(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }


    public User findByUsername(String username) {

        User userEntity = queryFactory
                .selectFrom(user)
                .where(
                        user.username.eq(username)
                        )
                .fetchOne();


        return userEntity;
    }

    public void save(User user) {
        em.merge(user);
    }

    public Optional<User> findById(Long id) {

        Optional<User> userEntity = Optional.ofNullable(queryFactory
                .selectFrom(user)
                .where(
                        user.id.eq(id)
                )
                .fetchOne());


        return userEntity;
    }

    public void deleteById(Long id) {



    }
}
