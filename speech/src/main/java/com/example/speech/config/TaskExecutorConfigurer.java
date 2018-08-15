package com.example.speech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @title: TaskExecutorConfigurer
 * @Description: TaskExecutor config
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
@Configuration
@EnableAsync
@EnableScheduling
public class TaskExecutorConfigurer {
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(256);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("task-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
