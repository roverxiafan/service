package com.example.qrlogin.controller;

import com.example.qrlogin.bean.DeviceInfo;
import com.example.qrlogin.service.AuthService;
import com.example.util.StringEx;
import com.example.web.result.R;
import com.example.web.util.IpUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @title: AuthController
 * @Description: 扫码登录认证
 * @author: roverxiafan
 * @date: 2017/4/17 15:42
 */
@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Resource
    private AuthService authService;

    @GetMapping(value = "/{token}", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public R authenticate(HttpServletRequest request,
                   @PathVariable("token") String token,
                   @RequestParam("uid") String uid,
                   @RequestParam("sid") String sid,
                   @RequestParam(value = "username",defaultValue = "") String username,
                   @RequestParam(value = "password", defaultValue = "") String password,
                   @RequestParam("key") String key) throws Exception {
        String ip = IpUtils.getClientIp(request);
        if(!redisTemplate.hasKey(token)) {
            return R.error(-1, "invalid token");
        }
        String validKey = StringEx.getString(redisTemplate.opsForHash().get(token, "secret"), "");
        if(!validKey.equals(key)) {
            return R.error(-2, "invalid key");
        }

        if(StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
            authService.validateLogin(uid, sid);
        }

        DeviceInfo devInfo = new DeviceInfo();
        Map map = redisTemplate.opsForHash().entries(token);
        BeanUtils.populate(devInfo, map);
        log.info("auth from ip:{} params=uid:{},sid:{},username:{},password:{},devInfo{}", ip, uid, sid, username, password, devInfo.toString());
        JSONObject json = authService.authenticate(uid, sid, devInfo);
        if(json.getInteger("ret") != 0) {
            return R.error(-5, json.getString("msg"));
        }
        redisTemplate.opsForHash().put(token, "result", json.toJSONString());
        return R.ok();
    }
}
