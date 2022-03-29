package com.kang.kanglog.repository.tag;

import com.kang.kanglog.config.db.P6spyPrettySqlFormatter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;



@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest // Repository들을 다 IOC 등록해둠.
@Import(TagCustomRepositoryImplTest.TestConfig.class)
class TagCustomRepositoryImplTest {


    @Autowired
    private TagRepository tagRepository;

    @Test
    void mFindUserTagsTest() {
        tagRepository.mFindUserTags(1L);

    }

    @TestConfiguration
    static class TestConfig {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public P6spyPrettySqlFormatter sqlFormatter(){
            return new P6spyPrettySqlFormatter();
        }

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }

    }
}