package com.example.demo.event;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @title: DemoEvent
 * @Description: 示例事件
 * @author: roverxiafan
 * @date: 2017/9/8 15:55
 */
@Slf4j
@Data
public class DemoEvent {
    private String ip = "127.0.0.1";
    private String name = "unknown";
    public DemoEvent(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }
}
