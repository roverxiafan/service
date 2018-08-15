package com.example.qrlogin.service;

import com.example.qrlogin.bean.DeviceInfo;
import com.example.util.StringEx;
import com.example.qrlogin.bean.DeviceInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @title: TokenService
 * @Description: 生成token
 * @author: roverxiafan
 * @date: 2017/6/7 20:08
 */
@Service
public class TokenService {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    private Random random = new Random();

    public String createToken(DeviceInfo deviceInfo) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        String token = StringEx.randomToken();

        while(!redisTemplate.opsForHash().putIfAbsent(token, "devId", deviceInfo.getDeviceID())) {
            token = StringEx.randomToken();
        }
        Map<String,String> map = BeanUtils.describe(deviceInfo);
        map.put("secret", randomSecret());
        redisTemplate.opsForHash().putAll(token, map);
        redisTemplate.expire(token, 5, TimeUnit.MINUTES);

        return token;
    }

    public String randomSecret() {
        StringBuilder secret = new StringBuilder(6);
        for (int j = 0; j < 6; j++) {
            secret.append(StringEx.DIGIT.charAt(random.nextInt(36)));
        }
        return secret.toString();
    }
}
