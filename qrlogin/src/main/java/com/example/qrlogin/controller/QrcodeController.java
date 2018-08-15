package com.example.qrlogin.controller;

import com.alibaba.fastjson.JSON;
import com.example.qrlogin.qrcode.QRCodeUtil;
import com.example.util.StringEx;
import com.example.web.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @title: QrcodeController
 * @Description: 生成二维码
 * @author: roverxiafan
 * @date: 2017/4/17 15:42
 */
@Slf4j
@Controller
@RequestMapping("/qrcode")
public class QrcodeController {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/{token}")
    public void out(@PathVariable("token") String token, HttpServletResponse response) throws Exception {
        try(OutputStream outputStream = response.getOutputStream()) {
            if(!redisTemplate.hasKey(token)) {
                outputStream.write(JSON.toJSONString(R.error(-1, "invalid token")).getBytes());
                outputStream.flush();
                return;
            }
            String secret = StringEx.getString(redisTemplate.opsForHash().get(token, "secret"), "");
            String content = String.format("http://192.168.3.11:8080/qrlogin/authc/%s?&secert=%s", token, secret);
            QRCodeUtil.encode(content, null, outputStream, true);
            outputStream.flush();
        }
    }
}
