package com.itheima.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 元注解：修饰注解的注解
@Target(ElementType.METHOD)         // 该注解只能加在方法上
@Retention(RetentionPolicy.RUNTIME) // 该注解在运行时生效
public @interface Log {

}
