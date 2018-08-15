package com.example.qrlogin.config;


import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @title: RestTemplateConfigurer
 * @Description: restTemplate配置
 * @author: roverxiafan
 * @date: 2017/06/29 09:38
 */
@Configuration
public class RestTemplateConfigurer {

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(3000);
        factory.setConnectTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().set(2, new FastJsonHttpMessageConverter());

        return restTemplate;
    }
}
