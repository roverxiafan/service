package com.example.demo.controller;

import com.example.common.aop.difftime.Difftime;
import com.example.exception.BaseException;
import com.example.web.common.PageInfo;
import com.example.web.result.R;
import com.example.web.result.RetCodeEnum;
import com.example.web.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;
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
import java.util.concurrent.TimeUnit;

/**
 * @title: UserController
 * @Description: redisson的分布式锁和分布式信号量
 * @author: roverxiafan
 * @date: 2017/01/29 10:21
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/limit")
public class LimitController {

    @Resource
    private RedissonClient redissonClient;


    @Difftime(desc="lock task",difftime = 1500)
    @RequestMapping(value = "/lock", produces = {"application/json;charset=UTF-8"})
    public R lockTask(@RequestParam(name = "value") @NotBlank(message = "value为空") String value) throws InterruptedException {
        RLock rLock = redissonClient.getLock(String.format("someprefix.%s", value));
        boolean locked = false;
        try {
            locked = rLock.tryLock(0, 3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("get lock exception", e);
        }
        System.out.println(locked);
        if(!locked) {
            throw new BaseException(RetCodeEnum.DUPLICATE_REQUEST.val(), RetCodeEnum.DUPLICATE_REQUEST.msg());
        }

        log.info("do something {}", value);
        Thread.sleep(2000);

        try{
            if(rLock.isLocked()) {
                rLock.unlock();
            }
        } catch (Exception e) {
            log.debug("rLock unlock", e);
        }

        return R.ok().put("status", 200).put("value", value);
    }

    @Difftime(desc="semaphore task",difftime = 1500)
    @RequestMapping(value = "/semaphore", produces = {"application/json;charset=UTF-8"})
    public R semaphoreTask() throws IOException {
        RPermitExpirableSemaphore semaphore = redissonClient.getPermitExpirableSemaphore("semaphore.speech.recognize");
        semaphore.trySetPermits(2);
        String permitId = null;
        try {
            permitId = semaphore.tryAcquire(100, 3000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("get semaphore exception", e);
        }

        if (permitId == null) {
            throw new BaseException("请求人数过多请稍后再试");
        }

        try {
            log.info("starting something");
            Thread.sleep(2000);
            log.info("finished something");
        } catch (InterruptedException e) {
            log.error("", e);
            Thread.currentThread().interrupt();
        }

        return R.ok().put("status", 200);
    }
}
