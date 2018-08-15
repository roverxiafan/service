package com.example.demo.controller;

import com.example.common.aop.difftime.Difftime;
import com.example.demo.service.AsyncService;
import com.example.web.common.PageInfo;
import com.example.web.result.R;
import com.example.web.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @title: UserController
 * @Description: 异步任务
 * @author: roverxiafan
 * @date: 2017/7/6 18:37
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = {"/async"})
public class AsyncController {
    @Resource
    private AsyncService asyncService;

    @Difftime(desc="test async task",difftime = 1500)
    @RequestMapping(value = "/task", produces = {"application/json;charset=UTF-8"})
    public R asyncTasks() throws InterruptedException, ExecutionException {
        log.info("start task ...");
        long start = System.currentTimeMillis();
        Future<String> task1 = asyncService.doTaskOne();
        Future<String> task2 = asyncService.doTaskTwo();
        Future<String> task3 = asyncService.doTaskThree();
        asyncService.doTaskFour();
        while(!(task1.isDone() && task2.isDone() && task3.isDone())) {
            Thread.sleep(50);
        }
        long end = System.currentTimeMillis();
        log.info("finish task，time：{}ms", end - start);
        return R.ok().put("taskOne", task1.get())
                .put("taskTwo", task1.get())
                .put("taskThree", task1.get());
    }
}
