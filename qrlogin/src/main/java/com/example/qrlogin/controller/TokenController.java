package com.example.qrlogin.controller;

import com.example.qrlogin.bean.DeviceInfo;
import com.example.qrlogin.service.TokenService;
import com.example.web.result.R;
import com.example.web.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * @title: QrcodeController
 * @Description: 生成token
 * @author: roverxiafan
 * @date: 2017/4/17 15:42
 */
@Slf4j
@Controller
@RequestMapping("/token")
public class TokenController {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Resource
    private TokenService tokenService;

    @RequestMapping(produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public R createToken(HttpServletRequest request, DeviceInfo deviceInfo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        deviceInfo.setIp(IpUtils.getClientIp(request));
        log.info("@@@@@@@@@@@@{}", deviceInfo.toString());
        return R.ok().put("token", tokenService.createToken(deviceInfo));
    }
}
