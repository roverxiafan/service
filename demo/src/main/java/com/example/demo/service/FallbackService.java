package com.example.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @title: FallbackService
 * @Description: Hystrix降级测试
 * @author: roverxiafan
 * @date: 2018/1/26 16:43
 */
@Slf4j
@Service
public class FallbackService {

    /**
     * hystrix降级测试
     * @param ms 执行时间
     * @return
     */
    @HystrixCommand(fallbackMethod = "taskFallback",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
    })
    public String task(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("", e);
        }

        return String.format("exec time %sms", ms);
    }

    public String taskFallback(long ms) {
        log.error(" fallback service fallback");
        return String.format("exec time %sms fallback", ms);
    }
}
