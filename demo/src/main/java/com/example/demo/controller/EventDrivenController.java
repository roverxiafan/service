package com.example.demo.controller;

import com.example.common.aop.difftime.Difftime;
import com.example.demo.event.DemoEvent;
import com.example.web.result.R;
import com.example.web.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @title: EventDrivenController
 * @Description: spring事件驱动
 * @author: roverxiafan
 * @date: 2017/9/8 15:55
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = {"/event"})
public class EventDrivenController {
    @Resource
    private ApplicationEventPublisher publisher;

    @Difftime(desc="EventDriven test",difftime = 1500)
    @RequestMapping(value = "/event-driven", produces = {"application/json;charset=UTF-8"})
    public R eventDrivenTask(HttpServletRequest request,
            @RequestParam(name = "name",required = false,defaultValue = "unknown") @NotBlank(message = "name不能为空") String name) {
        String ip = IpUtils.getClientIp(request);
        publisher.publishEvent(new DemoEvent(ip, name));
        return R.ok().put("ip", ip).put("name", name);
    }
}
