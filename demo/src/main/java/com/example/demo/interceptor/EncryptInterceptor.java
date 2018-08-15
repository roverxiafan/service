package com.example.demo.interceptor;

import com.example.web.result.RetCodeEnum;
import com.example.web.validator.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title: LoginInterceptor
 * @Description: web mvc 加解密拦截器
 * @author: roverxiafan
 * @date: 2018/1/9 10:38
 */
@Slf4j
@Component
public class EncryptInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Assert.isNull(request.getAttribute("encrypt"), RetCodeEnum.DECRYPT_ERROR.val(), RetCodeEnum.ENCRYPT_ERROR.msg());
        return true;
    }
}
