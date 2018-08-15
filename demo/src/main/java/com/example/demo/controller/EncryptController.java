package com.example.demo.controller;

import com.example.web.result.R;
import com.example.web.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @title: UserController
 * @Description: 请求加解密
 * @author: roverxiafan
 * @date: 2018/1/9 10:37
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "encrypt")
public class EncryptController {
    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = {"/ciphertext", "/plaintext"}, produces = {"application/json;charset=UTF-8"})
    public R encryptTask(@RequestParam(name = "value") @NotBlank(message = "value为空") String value,
                      HttpServletRequest request) throws UnsupportedEncodingException {
        value = URLDecoder.decode(value, "utf-8");
        String ip = IpUtils.getClientIp(request);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String redisKey = String.format("someprefix.%s", System.currentTimeMillis());
        redisTemplate.opsForValue().set(redisKey, df.format(new Date()), 5, TimeUnit.MINUTES);

        return R.ok().put("value", value)
                .put("ip", ip)
                .put("time", redisTemplate.opsForValue().get(redisKey));
    }
}
