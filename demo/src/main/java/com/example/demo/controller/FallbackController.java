package com.example.demo.controller;

import com.example.common.aop.difftime.Difftime;
import com.example.demo.service.FallbackService;
import com.example.web.common.PageInfo;
import com.example.web.result.R;
import com.example.web.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
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

/**
 * @title: FallbackController
 * @Description: Hystrix降级测试
 * @author: roverxiafan
 * @date: 2018/1/26 16:43
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = {"/fallback"})
public class FallbackController {
    @Resource
    private FallbackService fallbackService;


    @Difftime(desc="fallback test",difftime = 1500)
    @RequestMapping(value = "/example", produces = {"application/json;charset=UTF-8"})
    public R fallbackTask(
                      @RequestParam(name = "time", required = false, defaultValue = "100")
                      @Range(min=100, max = 2000, message = "time 100-2000") long time
                     ) {
        log.info("start task ...");
        long start = System.currentTimeMillis();
        String result = fallbackService.task(time);
        long end = System.currentTimeMillis();
        long difftime = end-start;
        log.info("finish task，time：{}ms", end - start);

        return R.ok().put("time", time)
                .put("real time", difftime)
                .put("string", result);
    }
}
