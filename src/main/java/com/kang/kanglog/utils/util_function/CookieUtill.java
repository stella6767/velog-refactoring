package com.kang.kanglog.utils.util_function;

import com.kang.kanglog.utils.common.JwtProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class CookieUtill  {

    public static Cookie createNullCookie(String cookieName){
        Cookie token = new Cookie(cookieName, null);
        token.setHttpOnly(false);
        token.setMaxAge(0);// Don't set to -1 or it will become a session cookie!
        token.setPath("/");
        return token;
    }


    public static Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(false);
        token.setMaxAge((int) JwtProperties.ACCESS_TOKEN_VALIDATION_SECOND);
        token.setPath("/");
        return token;
    }

    public static Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }

}