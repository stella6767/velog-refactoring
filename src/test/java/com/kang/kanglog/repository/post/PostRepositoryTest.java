package com.kang.kanglog.repository.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations={"classpath:application.yml"}, properties = {"spring.config.location=classpath:aws.yml"})
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;


    @Test
    void mLikeList() {
    }

    @Test
    void mFindByKeyword() {
    }

    @Test
    void mTrendingTest() {
        PageRequest request = PageRequest.of(0,10);
        postRepository.mTrending(request);
    }

    @Test
    void mfindAllByPage() {
    }
}