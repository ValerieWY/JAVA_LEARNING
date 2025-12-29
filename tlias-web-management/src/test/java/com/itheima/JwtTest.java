package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    // 生成JWT令牌，调用工具类Jwts
    @Test
    public void testGenerateJwt() {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");

        // 点击到signWith中去下载源码发现，
        // JwtBuilder signWith(SignatureAlgorithm alg, String base64EncodedSecretKey);
        // 需要一个Base64编码的密钥
        // 找一个Base64转码网站 itheima：aXRoZWltYQ==
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "aXRoZWltYQ==")  // 指定加密算法，密钥
                .addClaims(dataMap)         // 自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))    // 设置过期时间为当前时间开始往后的1h，一个小时对应的毫秒值
                .compact() ;                // 生成令牌

        System.out.println(jwt); // 可以再返回Base64网站解码
        // eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc2NjkwNzQwMX0.c6DZpOgcMB54uskBpZdizuWbQ1fHQvTj3puB1je7gOg
    }

    // 解析JWT令牌
    @Test
    public void testParseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc2NjkwNzQwMX0.c6DZpOgcMB54uskBpZdizuWbQ1fHQvTj3puB1je7gOg";
        Claims claims = Jwts.parser()
                .setSigningKey("aXRoZWltYQ==") // 解析令牌需要的密钥要和生成令牌时使用的密钥完全一致
                .parseClaimsJws(token)             // 解析令牌
                .getBody();     // 获取令牌中第二个部分的自定义信息，就是.addClaims(dataMap)中的dataMap
        System.out.println(claims);
        // {id=1, username=admin, exp=1766907401}
        // 一旦改动了一处原令牌中的内容，就会抛出异常
        // 如果过期了，解析也会报错
    }

}
