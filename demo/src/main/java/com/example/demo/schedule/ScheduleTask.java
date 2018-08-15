package com.example.demo.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @title: RecognizeScheduleTask
 * @Description: 定时任务
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
@Slf4j
@Component
public class ScheduleTask {
    /**
     * 定时任务每分钟执行
     */
    @Scheduled(cron = "0 */1 * * * *")
    public void recognizeScheduleTask() {
        log.info("ScheduleTask now:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}

