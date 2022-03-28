package com.kang.kanglog.config.anno;

import java.lang.annotation.*;

@Documented // javadoc으로 api 문서를 만들 때 어노테이션에 대한 설명도 포함하도록 지정
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface LoginCheck {



}
