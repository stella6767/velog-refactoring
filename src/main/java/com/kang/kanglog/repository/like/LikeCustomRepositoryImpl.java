package com.kang.kanglog.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.kang.kanglog.domain.QLike.like;

@Slf4j
@Repository
public class LikeCustomRepositoryImpl implements LikeCustomRepository {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public LikeCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager em) {
        this.queryFactory = queryFactory;
        this.em = em;
    }


    @Override
    public long mUnLike(Long postId, Long principalId) {

        /**
         * delete를 쓰기보다는 다른 식으로 변경하는 게 낫겠지만.. (사용여부만 n 해서 주기 잡아서 한 번에 bulk delete..)
         * 어차피 내 맘대로 진행하는 거니까 편하게 하겠다. querydsl로 단 건 delete를 해도 되나? 예시가 별로 없어서..
         */
        long count = queryFactory
                .delete(like)
                .where(
                        like.post.id.eq(postId),
                        like.user.id.eq(principalId)
                )
                .execute();
//        em.flush();
//        em.clear();

        return count;
    }
}
