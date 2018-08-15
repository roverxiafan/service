package com.example.demo.config;

import com.example.web.exception.BaseExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @title: DemoExceptionHandler
 * @Description: 配置异常处理器
 * @author: roverxiafan
 * @date: 2018/1/19 18:36
 */
@Slf4j
@RestControllerAdvice
public class DemoExceptionHandler extends BaseExceptionHandler {
}
