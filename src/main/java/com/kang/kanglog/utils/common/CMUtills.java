package com.kang.kanglog.utils.common;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


@Slf4j
public class CMUtills {


//    public static List<Tag> parsingToTagObject(String tags, Post postEntity){
//        String temp[] = tags.split("#"); // #여행 #바다
//        List<Tag> list = new ArrayList<>();
//
//        // 도라에몽 : 파싱할 때 0번지에 공백이 들어와서 시작번지를 1로 함.
//        for (int i=1; i<temp.length; i++) {
//            Tag tag = Tag.builder()
//                    .name(temp[i].trim())
//                    .post(postEntity)
//                    .build();
//            list.add(tag);
//        }
//
//        return list;
//    }
//


    public static Map<String, Object> getHeadersInfo(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = ((String) headerNames.nextElement()).toUpperCase();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }


    public static <T> boolean filterBoolean(List<T> list, Predicate<T> filter) {

        boolean result = false;

        for (T input : list) {
            if (filter.test(input)) {
                result = true;
            }
        }
        return result;
    }



}
