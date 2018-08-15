package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @title: AsyncService
 * @Description: 异步任务
 * @author: roverxiafan
 * @date: 2017/7/6 18:37
 */
@Slf4j
@Service
public class AsyncService {

    @Async
    public Future<String> doTaskOne() throws InterruptedException{
        log.info("start task one ...");
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        log.info("finish task one，time：{}ms", end - start);
        return new AsyncResult<>("task1");
    }

    @Async
    public Future<String> doTaskTwo() throws InterruptedException {
        log.info("start task two ...");
        long start = System.currentTimeMillis();
        Thread.sleep(2000);
        long end = System.currentTimeMillis();
        log.info("finish task two，time：{}ms", end - start);
        return new AsyncResult<>("task2");
    }

    @Async
    public Future<String> doTaskThree() throws InterruptedException {
        log.info("start task three ...");
        long start = System.currentTimeMillis();
        Thread.sleep(500);
        long end = System.currentTimeMillis();
        log.info("finish task three，time：{}ms", end - start);
        return new AsyncResult<>("task3");
    }

    @Async
    public void doTaskFour() throws InterruptedException  {
        log.info("start task four ...");
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();
        log.info("finish task four，time：{}ms", end - start);
    }
}
