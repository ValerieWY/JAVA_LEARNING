package com.itheima.filter;

import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")      // 若注解掉了就不会生效了
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 请求对象servletRequest中封装了所有请求参数，但类型是HttpServletRequest，需要强转
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 响应对象同理
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取请求路径
        String path = request.getRequestURI();

        // 2、判断是否登录请求（是否包含/login），放行
        if (path.contains("/login")) {
            log.info("登录请求：{}", path);
            filterChain.doFilter(request, response);
            return;
        }

        // 3、获取token
        String token = request.getHeader("token");

        // 4、判断token是否存在，未存在则返回未登录（响应401状态码）
        if (token == null || token.isEmpty()) {
            log.info("令牌为空，响应401状态码。");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 直接写401也行
            return;
        }

        // 5、token存在则校验令牌，校验失败也返回错误信息（响应401状态码）
        try {
            Claims claims = JwtUtils.parseToken(token); // 获取token中的第二部分自定义内容
            // map<String,Object>
            Integer empId = Integer.valueOf(claims.get("id").toString());   // 获取员工ID
            CurrentHolder.setCurrentId(empId);  // 存入员工ID到当前线程变量中
            log.info("当前登录用户ID：{}", empId);
        } catch (Exception e) {
            log.info("令牌非法，响应401状态码。");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 6、校验成功则放行
        log.info("令牌合法，放行。");
        filterChain.doFilter(request, response);    // 访问对应的资源结束后，会返回执行后面的逻辑

        // 7、当前线程变量清除
        CurrentHolder.remove();
    }
}
