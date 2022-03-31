package com.kang.kanglog.utils.util_function;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ImageUtil {


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


    public static String fileToBase64String(File f) {
        String strBase64 = "";
        if (f.exists() && f.isFile() && f.length() > 0) {
            byte[] bt = new byte[(int) f.length()];
            FileInputStream fis = null;

            //effective java에서는 try with resource를 사용하라고 권장한다. 나도 그렇게 따라야할까?
            try {
                fis = new FileInputStream(f);
                fis.read(bt);
                strBase64 = new String(Base64.encodeBase64(bt));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return strBase64;
    }


    public static String getImgSrc(String str) {

        Pattern p = Pattern.compile("<img[^>]*src=[\"']?([^>\"']*(?:base64)+[^>\"']+)[\"']?[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);

        String group = "";

        while (m.find()) {
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


}
