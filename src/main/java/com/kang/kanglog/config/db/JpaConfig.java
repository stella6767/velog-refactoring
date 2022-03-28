package com.kang.kanglog.config.db;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.p6spy.engine.spy.P6SpyOptions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@EnableJpaAuditing // JPA Auditing 활성화
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean //동시성 문제 신경 안 써도 됨. 트랜잭션 붙여주면 트랜잭션 단위로 다 다른 것으로 할당해서 바인딩해줌
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }


    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spyPrettySqlFormatter.class.getName());

        // https://backtony.github.io/spring/2021-08-13-spring-log-1/
    }


    @Bean
    public ModelMapper modelMapper(){
        /**
         * 공식문서에 threadsafe하다 적혀있으니 전역 싱글톤으로 등록해도 상관없겠지..
         * https://devwithpug.github.io/java/java-modelmapper/
         * https://lokie.tistory.com/26
         * modelmapper는 setter를 이용해서 mapping 한다..
         * 엔티티에 setter를 넣고 싶지 않을 상황시 설정을 아래와 같이
         */

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }


    @Bean
    public ObjectMapper objectMapper() {
        //Thread safe 하다고 했으니까 싱글톤으로 등록해도 되겠지..
        //https://umanking.github.io/2021/07/24/jackson-localdatetime-serialization/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //아직 불러오지 않은 엔티티에 대해 null값을 내려주는 모듈이다. lazy loading
        objectMapper.registerModule(new Hibernate5Module());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

}
