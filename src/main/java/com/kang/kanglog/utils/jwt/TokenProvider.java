package com.kang.kanglog.utils.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import net.lunalabs.mj.config.security.PrincipalDetails;

import java.util.Date;

public class TokenProvider {


    public static String createToken(PrincipalDetails principalDetails){
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getMemberUserId())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getOporator().getId())
                .withClaim("pass", principalDetails.getOporator().getPassword())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }

}
