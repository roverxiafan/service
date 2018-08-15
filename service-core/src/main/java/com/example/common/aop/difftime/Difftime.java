package com.example.common.aop.difftime;

import java.lang.annotation.*;

/**
 * @title: Difftime
 * @Description: 监控方法执行时间注解
 * @author: roverxiafan
 * @date: 2016/11/15 11:29
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Difftime {
    int difftime() default 1000;
    String desc() default "";
}
