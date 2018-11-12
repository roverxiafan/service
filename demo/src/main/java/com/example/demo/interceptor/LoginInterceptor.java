package com.example.demo.interceptor;

import com.example.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Value("${spring.profiles}")
    private String env;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(ExcludeLogin.class) || method.isAnnotationPresent(ExcludeLogin.class)) {
                return true;
            }

            if (!isLogined(request)) {
                throw new BaseException(1001, "用户未登录");
            }
        }

        return true;
    }

    private boolean isLogined(HttpServletRequest request) {
        return true;
    }


}
