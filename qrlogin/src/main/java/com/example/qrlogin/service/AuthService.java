package com.example.qrlogin.service;

import com.alibaba.fastjson.JSONObject;
import com.example.qrlogin.bean.DeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @title: TokenService
 * @Description: 登录认证
 * @author: roverxiafan
 * @date: 2017/6/7 20:08
 */
@Slf4j
@Service
public class AuthService {
    public JSONObject authenticate(String uid, String sid, DeviceInfo deviceInfo) {
        JSONObject json = new JSONObject();
        json.put("uid", uid);
        json.put("sid", "123123");
        json.put("nickname", "nickname");
        json.put("headPic", "http://localhost/image/xx.jpg");
        json.put("ret", 0);
        return json;
    }

    public void validateLogin(String uid, String sid) {
//        throw new BaseException(RetCodeEnum.INVALID_SID.val(), RetCodeEnum.INVALID_SID.msg());
    }
}
