package com.kang.kanglog.repository.post;

import com.kang.kanglog.domain.Post;
import com.kang.kanglog.web.dto.post.PostResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@TestPropertySource(locations={"classpath:application.yml"}, properties = {"spring.config.location=classpath:aws.yml"})
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCustomRepositoryImpl customRepository;


    @Test
    void mLikeList() {
    }

    @Test
    void mFindByKeyword() {
    }

    @Test
    void mTrendingTest() {
        PageRequest request = PageRequest.of(0,10);

        Page<Post> posts = postRepository.mfindAllByPage(request);

        List<PostResDto.PostDto> collect = posts.map(entity -> {
            PostResDto.PostDto dto = new PostResDto.PostDto(1L, entity);
            return dto;
        }).stream().sorted(Comparator.comparing(PostResDto.PostDto::getLikeCount)).collect(Collectors.toList());


        Page<PostResDto.PostDto> postDtos = new PageImpl<>(collect);

        List<PostResDto.PostDto> content = postDtos.getContent();

        System.out.println(content);

    }


}