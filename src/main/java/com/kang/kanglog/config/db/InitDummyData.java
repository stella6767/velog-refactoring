package com.kang.kanglog.config.db;


import com.github.javafaker.Faker;
import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.domain.User;
import com.kang.kanglog.service.PostService;
import com.kang.kanglog.utils.util_function.UtilManager;
import com.kang.kanglog.web.dto.post.PostReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Locale;

@Slf4j
@Profile("local")
@Configuration
@RequiredArgsConstructor
public class InitDummyData implements CommandLineRunner {

    private final PostService postService;
    private final ResourceLoader resourceLoader;

    Faker faker = new Faker(new Locale("ko"));

    @Override
    public void run(String... args) throws Exception {
        //insertDummyPost(100);
    }



    private PostReqDto.PostSaveReqDto createDummyPost() throws IOException {

        Resource resource = resourceLoader.getResource("classpath:static/img/dummy.png");
        //System.out.println(resource.exists());
        String base64String = UtilManager.fileToBase64String(resource.getFile());

        String fakerContent = faker.matz().quote();
        String title = faker.hipster().word();

        StringBuilder sb = new StringBuilder();
        sb.append("<body>");
        sb.append("<img src=" + base64String + " >");
        sb.append("<p>");
        sb.append(fakerContent);
        sb.append("</p>");
        sb.append("</body>");

        PostReqDto.PostSaveReqDto postSaveReqDto = new PostReqDto.PostSaveReqDto(title, sb.toString(), null);

        //log.info(postSaveReqDto.toString());
        //requestLoggerAdvice 잠시 disable 하자.. request 정보를 찾을 수 없네.
        //no thread-bound request found

        return postSaveReqDto;
    }

    private void insertDummyPost(int num) {

        for (int i = 0; i < num; i++) {

            PostReqDto.PostSaveReqDto dummyPost = null;
            try {
                dummyPost = createDummyPost();
            } catch (IOException e) {
                e.printStackTrace();
            }
            postService.저장하기(dummyPost,  new PrincipalDetails(new User(1L)));
        }

    }


}
