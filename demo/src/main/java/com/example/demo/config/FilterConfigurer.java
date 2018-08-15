package com.example.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @title: FilterConfigurer
 * @Description: 拦截器配置
 * @author: roverxiafan
 * @date: 2018/1/19 18:36
 */
@Configuration
public class FilterConfigurer {

    @Bean
    public FilterRegistrationBean encryptFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("encryptionFilter"));
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE + 1);
        return registration;
    }

    /**
     * 配置参数验证
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}

