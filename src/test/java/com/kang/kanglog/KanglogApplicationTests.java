package com.kang.kanglog;

import com.kang.kanglog.repository.tag.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
//        properties = "spring.config.location=" +
//                "resources/application.yml" +
//                ",classpath:/aws.yml"

)

/**
 * 다른 폴더 경로에 있는 거 같이 설정이 안 되네..
 */

//@TestPropertySource(properties = {  "spring.config.location=classpath:application.yml","spring.config.location=classpath:aws.yml"})
@TestPropertySource(locations={"classpath:application.yml"}, properties = {"spring.config.location=classpath:aws.yml"})
class KanglogApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private TagRepository tagRepository;

    @Test
    void mFindUserTagsTest() {
        tagRepository.mFindUserTags(1L);

    }
}
