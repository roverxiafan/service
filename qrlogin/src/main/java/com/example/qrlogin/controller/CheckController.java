package com.example.qrlogin.controller;


import com.example.util.StringEx;
import com.example.web.result.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @title: CheckController
 * @Description: 检测登录结果
 * @author: roverxiafan
 * @date: 2017/4/17 15:42
 */
@Slf4j
@Controller
@RequestMapping("/check")
public class CheckController {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/{token}", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public R loginRes(@PathVariable("token") String token) {
        if(!redisTemplate.hasKey(token)) {
            return R.error(-1, "invalid token");
        }
        String jsonStr = StringEx.getString(redisTemplate.opsForHash().get(token, "result"), "");
        if(StringUtils.isBlank(jsonStr)) {
            return R.error(1001, "waiting");
        }
        JSONObject json = JSON.parseObject(jsonStr);
        return R.ok().put(json);
    }
}
