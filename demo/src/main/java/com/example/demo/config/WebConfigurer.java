package com.example.demo.config;


import com.example.demo.interceptor.EncryptInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @title: WebConfigurer
 * @Description: wev mvc配置
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
@Configuration
@EnableScheduling
public class WebConfigurer implements WebMvcConfigurer {
    @Resource
    private EncryptInterceptor encryptInterceptor;

    /**
     * 拦截器
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(encryptInterceptor).addPathPatterns("/encrypt/**").excludePathPatterns("/encrypt/plaintext");
    }
}
