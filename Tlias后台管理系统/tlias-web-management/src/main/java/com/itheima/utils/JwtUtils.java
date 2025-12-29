package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "aXRoZWltYQ==";  // 密钥
    private static final long EXPIRATION_TIME = 12*60*60*1000; // 12h

    /**
     * 生成JWT令牌
     * @param claims 令牌中要包含的参数
     * @return 生成JWT的令牌字符串
     */
    public static String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param token 要解析的JWT令牌
     * @return 包含令牌信息的Claims对象
     * @throws Exception 如果令牌无效或已过期，则抛出异常
     */
    public static Claims parseToken(String token) throws Exception{
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();     // 获取令牌中第二个部分的自定义信息
    }
}
