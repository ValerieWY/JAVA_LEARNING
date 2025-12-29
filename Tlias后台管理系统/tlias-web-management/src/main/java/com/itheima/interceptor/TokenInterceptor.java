package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// 令牌校验的拦截器
// 还要去WebConfig中注册才可使用哦
@Slf4j
@Component
public class TokenInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*        // 1、获取请求路径
        String path = request.getRequestURI();

        // 2、判断是否登录请求（是否包含/login），放行
        if (path.contains("/login")) {
            log.info("登录请求：{}", path);
            return true;
        }*/

        // 3、获取token
        String token = request.getHeader("token");

        // 4、判断token是否存在，未存在则返回未登录（响应401状态码）
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，响应401状态码。");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 直接写401也行
            return false;
        }

        // 5、token存在则校验令牌，校验失败也返回错误信息（响应401状态码）
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌非法，响应401状态码。");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 6、校验成功则放行
        log.info("令牌合法，放行。");
        return true;
    }
}
