package com.kang.kanglog.utils.util_function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kang.kanglog.utils.common.CMResDto;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class Script  {

    
    
    public static void responseData(HttpServletResponse resp, CMResDto<?> cmResDto) throws IOException {
        PrintWriter out;

        /**
         * 불필요하지만.. 딱히 ObjectMapper 인젝션 좋은 방법이 생각이 안 나서. 이대로 놔두겠다.
         */

        ObjectMapper om = new ObjectMapper();

        String jsonData = om.writeValueAsString(cmResDto);
        log.info("응답 데이터: " + jsonData);

        resp.setHeader("Content-Type", "application/json; charset=utf-8");

        try {
            out = resp.getWriter();
            out.println(jsonData);
            out.flush(); //버퍼 비우기
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void responseData(HttpServletResponse resp, CMResDto<?> cmResDto, ObjectMapper om) throws IOException {
        PrintWriter out;

        String jsonData = om.writeValueAsString(cmResDto);
        log.info("응답 데이터: " + jsonData);

        resp.setHeader("Content-Type", "application/json; charset=utf-8");

        try {
            out = resp.getWriter();
            out.println(jsonData);
            out.flush(); //버퍼 비우기
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
