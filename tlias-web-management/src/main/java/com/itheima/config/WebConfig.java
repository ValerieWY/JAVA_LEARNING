package com.itheima.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 配置类（底层封装的就是@Component）
public class WebConfig implements WebMvcConfigurer {
    // 注入拦截器
//    @Autowired
//    private DemoInterceptor demoInterceptor;

/*    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")    // 添加拦截器并指定拦截路径
                .excludePathPatterns("/login");  // 登录请求不拦截
    }*/
}
