package com.example.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @title: DemoEventListener
 * @Description: 示例事件监听器
 * @author: roverxiafan
 * @date: 2017/9/8 15:56
 */
@Slf4j
@Component
public class DemoEventListener {


    /**
     * 事件驱动测试
     * @param demoEvent
     */
    @Async
    @EventListener
    @Order(1)
    public void printDemoEvent(DemoEvent demoEvent) {
    log.info("start demo event");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("", e);
            Thread.currentThread().interrupt();
        }
        log.info("demo event name:{}, ip:{}", demoEvent.getName(), demoEvent.getIp());
    }
}
