package com.kang.kanglog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kang.kanglog.utils.converter.HTMLCharacterEscapes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer { //View 컨트롤, Model 컨트롤, Controller




//    public void addInterceptors(InterceptorRegistry registry) {
//
//		//registry.addInterceptor(new LoggingInterceptor());
//
////		registry.addInterceptor(new AdminLoggerInterceptor())
////				.addPathPatterns("/user").addPathPatterns("/post");
//    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);

        converters.add(htmlEscapingConveter());
    }


    private HttpMessageConverter<?> htmlEscapingConveter() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 3. ObjectMapper에 특수 문자 처리 기능 적용
        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

        // 4. MessageConverter에 ObjectMapper 설정
        MappingJackson2HttpMessageConverter htmlEscapingConverter =
                new MappingJackson2HttpMessageConverter();
        htmlEscapingConverter.setObjectMapper(objectMapper);

        return htmlEscapingConverter;
    }


/*    lucy-xss-servlet-filter는 JSON에 대한 XSS는 처리해주지 않는다.
    따라서, JSON에 대한 XSS가 필요하다면
    Jackson의 com.fasterxml.jackson.core.io.CharacterEscapes를 상속하는 클래스 A를 직접 만들어서 처리해야 할 특수문자를 지정하고,
    ObjectMapper에 A를 설정하고,
    ObjectMapper를 MessageConverter에 등록해서 Response가 클라이언트에 나가기 전에 XSS 방지 처리 해준다.*/


//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//      굳이 쓰지 않겠다..
//
//    }
}