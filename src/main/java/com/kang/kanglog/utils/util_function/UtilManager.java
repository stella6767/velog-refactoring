package com.kang.kanglog.utils.util_function;

import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.domain.Post;
import com.kang.kanglog.domain.Tag;
import com.kang.kanglog.domain.User;
import com.kang.kanglog.service.RedisService;
import com.kang.kanglog.utils.common.CMResDto;
import com.kang.kanglog.utils.common.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

@Slf4j
public class UtilManager {


    /**
     *
     */

    private UtilManager() { //외부에서 new 못하도록 막아놓고.
    }
    //방법 4 = static inner 클래스 사용, 사실 전부 static method라 요런 방법 필요없긴 한데, 나중을 대비해서,,
    private static class UtilManagerHolder {
        private static final UtilManager INSTANCE = new UtilManager();
    }
    public static UtilManager getInstance(){
        return UtilManagerHolder.INSTANCE;
    }






    /**
     *
     */

    public static List<Tag> parsingToTagObject(String tags, Post postEntity){

        return CMUtil.parsingToTagObject(tags,postEntity);
    }



    public static Map<String, Object> getHeadersInfo(HttpServletRequest request) {

        return CMUtil.getHeadersInfo(request);
    }


    public static <T> boolean filterBoolean(List<T> list, Predicate<T> filter) {

        return CMUtil.filterBoolean(list,filter);
    }


    public static void responseData(HttpServletResponse resp, CMResDto<?> cmResDto) throws IOException {

        Script.responseData(resp, cmResDto);
    }


    /**
     * 이미지 관련
     */


    public static String getImgSrc(String str) {

        return ImageUtil.getImgSrc(str);
    }

    public static List<String> getImgSrcList(String str) {

        return ImageUtil.getImgSrcList(str);
    }

    public static String fileToBase64String(File file) {

        return ImageUtil.fileToBase64String(file);
    }




    /**
     * 쿠키 관련 서비스
     */

    public HttpServletResponse generateLoginCookie(HttpServletResponse response, PrincipalDetails principalDetails, RedisService redisService) {
        //이를 활용해 Token을 만들고
        final String accessToken = generateAccessToken(principalDetails.getUser().getId());
        final String refreshToken = generateRefreshToken(principalDetails.getUser().getId());

        //cookie로 만듬
        Cookie accessCookie = createCookie(JwtProperties.ACCESS_TOKEN_NAME, accessToken);
        Cookie refreshCookie = createCookie(JwtProperties.REFRESH_TOKEN_NAME, refreshToken);

        //RefreshToken을 Redis에 저장
        redisService.setDataExpire(refreshToken, principalDetails.getUser().getId().toString(),
                JwtProperties.REFRESH_TOKEN_VALIDATION_SECOND);

        //이제 이 쿠키를 클라이언트 서버로
        //response.setHeader("Set-Cookie", "key=value; HttpOnly; SameSite=strict");
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        return response;
    }




    public static Cookie createNullCookie(String cookieName){
        return CookieUtil.createNullCookie(cookieName);
    }


    public static Cookie createCookie(String cookieName, String value){
        return CookieUtil.createCookie(cookieName,value);
    }

    public static Cookie getCookie(HttpServletRequest req, String cookieName){

        return CookieUtil.getCookie(req,cookieName);
    }


    /**
     * JWT 토큰 관련 서비스
     */


    public static Claims extractAllClaims(String token) throws ExpiredJwtException {
        return JWTUtil.extractAllClaims(token);
    }

    public static Long getUserId(String token) {

        return JWTUtil.getUserId(token);
    }

    public static Boolean isTokenExpired(String token) {
        return JWTUtil.isTokenExpired(token);
    }



    public static String generateAccessToken(Long userId) {

        return JWTUtil.generateAccessToken(userId);
    }

    public static String generateRefreshToken(Long userId) {

        return JWTUtil.generateRefreshToken(userId);
    }



    public static Boolean validateToken(String token, User user) {
        return JWTUtil.validateToken(token, user);

    }



}
