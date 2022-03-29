package com.kang.kanglog.utils.util_function;

import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.Tag;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class CMUtills  {


    public static List<Tag> parsingToTagObject(String tags, Post postEntity){
        String temp[] = tags.split("#"); // #여행 #바다
        List<Tag> list = new ArrayList<>();
        // 파싱할 때 0번지에 공백이 들어와서 시작번지를 1로 함.
        for (int i=1; i<temp.length; i++) {
            Tag tag = new Tag();
            tag.add(temp[i].trim(), postEntity);
            list.add(tag);
        }
        return list;
    }


    public static String getImgSrc(String str) {

        Pattern p = Pattern.compile("<img[^>]*src=[\"']?([^>\"']*(?:base64)+[^>\"']+)[\"']?[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);

        String group = "";

        while( m.find() ){
            group = m.group(1);
            log.info(">>>>>" + group);
        }

//        //모든 html src를 받아도 img src의 첫부분만 return
//        Pattern nonValidPattern = Pattern
//                .compile("(?i)< *[IMG][^\\>]*[src] *= *[\"\']{0,1}([^\"\'\\ >]*)");
//        int imgCnt = 0;
//        String content = "";
//        Matcher matcher = nonValidPattern.matcher(str);
//        while (matcher.find()) {
//            content = matcher.group(1);
//            imgCnt++;
//            if(imgCnt == 1){
//                break;
//            }
//        }
        return group;
    }

    public static List getImgSrcList(String str) {
        Pattern nonValidPattern = Pattern
                .compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

        List result = new ArrayList();
        Matcher matcher = nonValidPattern.matcher(str);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }







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
