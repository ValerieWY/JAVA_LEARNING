package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping
    public Result login(@RequestBody Emp emp) {     // 将json格式的数据封装到一个实体类当中，用@RequestBody注解
        log.info("登录信息：{}", emp);
        LoginInfo loginInfo = empService.login(emp);
        if(loginInfo != null) {
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }
}
