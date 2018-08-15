package com.example.qrlogin.interceptor;

import com.example.qrlogin.service.AuthService;
import com.example.util.StringEx;
import com.example.web.validator.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title: LoginInterceptor
 * @Description: 拦截器
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uid = StringEx.getString(request.getParameter("uid"));
        String sid = StringEx.getString(request.getParameter("sid"));
        Assert.isUid(uid);
        Assert.isUid(sid);

        authService.validateLogin(uid, sid);

        return true;
    }
}
