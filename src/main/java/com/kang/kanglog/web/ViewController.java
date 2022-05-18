package com.kang.kanglog.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class ViewController {



    @GetMapping("/")
    public String index(){

        /**
         * react에서 빌드한 결과물 (빌드 폴더 안의 파일들) 을 static 폴더 안 쪽에 위치
         */


        return "/index.html";
    }


}
