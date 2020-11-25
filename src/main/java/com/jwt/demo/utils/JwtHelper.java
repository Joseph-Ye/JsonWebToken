package com.jwt.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jhye4
 * @date 2020/11/23 16:10
 * JWT 工具类
 */
public class JwtHelper {

    private static final String SECRET = "SESSION_SECRET";

    private static final String ISSUE = "MY_USER";

    /**
     * 未做异常处理 IllegalArgumentException | UnsupportedEncodingException
     */
    public static String genToken(Map<String, String> claims){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUE);
            claims.forEach((k,v)->builder.withClaim(k, v));
            return builder.sign(algorithm);
        }catch (IllegalArgumentException exception){
            throw new RuntimeException(exception);
        }

    }

    public static Map<String, String> verifyToken(String token){
        Algorithm algorithm;
        try{
            algorithm = Algorithm.HMAC256(SECRET);
        }catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }

        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUE).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> map = jwt.getClaims();
        Map<String, String> resultMap = new HashMap<>();
        // 转化封装  返回原有的claims
        map.forEach((k,v)->resultMap.put(k, v.toString()));
        return resultMap;
     }

}
