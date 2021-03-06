package com.kang.kanglog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class KanglogApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:aws.yml";

    public static void main(String[] args) {

        //-Dcom.amazonaws.sdk.disableEc2Metadata=true  VMOption
        //System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
        //SpringApplication.run(KanglogApplication.class, args);

        new SpringApplicationBuilder(KanglogApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);

    }


    /**
     * 퇴근 후 사이드 프로젝트를 함 해보자.. 새로운 컨셉 정하는 거 머리 아프니.. 예전에 하다 만 거 조금 더 고쳐서 배포해보자..
     *
     * 목표는 계층형 댓글 구현과 자동배포 환경 구축, s3 이미지 업로드, testcade 작성, websocket 실시간 연동 메시지 알림
     * sse 프로토콜 단방향 차트 구현 .... 생각나는 데까지만 하자.. userprofile 수정, 소셜 정보. 이메일 주소, 이미지제거, 임시 글
     *
     * admin page는 mustache로?
     */


}
