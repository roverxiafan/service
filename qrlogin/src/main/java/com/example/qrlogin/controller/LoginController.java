package com.example.qrlogin.controller;

import com.example.qrlogin.bean.DeviceInfo;
import com.example.qrlogin.service.TokenService;
import com.example.util.StringEx;
import com.example.web.util.IpUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * @title: LoginController
 * @Description: 扫码登录
 * @author: roverxiafan
 * @date: 2017/4/17 15:42
 */
@Controller
@RequestMapping
public class LoginController {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Resource
    private TokenService tokenService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model, DeviceInfo deviceInfo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        deviceInfo.setIp(IpUtils.getClientIp(request));
        String token = tokenService.createToken(deviceInfo);
        model.addAttribute("token", token);
        return "login";
    }

    @RequestMapping("/authc/{token}")
    public String authc(Model model, @PathVariable("token") String token,
                        @RequestParam(name = "secert") String secert) {
        if(!redisTemplate.hasKey(token)) {
            return "invalid";
        }
        String validKey = StringEx.getString(redisTemplate.opsForHash().get(token, "secret"), "");
        if(!validKey.equals(secert)) {
            return "invalid";
        }
        model.addAttribute("token", token);
        model.addAttribute("secret", secert);
        return "authc";
    }
}
