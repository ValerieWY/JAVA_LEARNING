package com.itheima.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component // 拦截器是sb提供的技术，需要交给IOC容器管理
// 拦截路径配置单独是一个类
public class DemoInterceptor implements HandlerInterceptor {

    // 在目标资源方法执行之前执行 ——返回值为true表示放行，返回值为false表示拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        return true;
    }

    // 在目标资源方法执行之后，视图渲染之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("postHandle...");
    }

    // 在视图渲染完成之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("afterCompletion...");
    }
}
