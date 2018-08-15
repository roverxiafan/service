package com.example.qrlogin.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * @title: JsonpAdvice
 * @Description: jsonp配置
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
@RestControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonpAdvice() {
        super("callback", "jsonp");
    }
}
