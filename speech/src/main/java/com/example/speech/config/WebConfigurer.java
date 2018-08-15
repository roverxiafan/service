package com.example.speech.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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

//    @Resource
//    private LoginInterceptor loginInterceptor;
    /**
     * 拦截器
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/auth/*");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/upload").setViewName("upload");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(1800L);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
