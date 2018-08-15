package com.example.common.aop.difftime;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @title: DifftimeAspect
 * @Description: 方法执行时间大于期望时间打印警告日志
 * @author: roverxiafan
 * @date: 2016/11/15 11:29
 */
@Slf4j
@Component
@Aspect
@Order(1)
public class DifftimeAspect {
    @Pointcut(value="@annotation(difftime) && execution(public * *(..))")
    private void pointcut(Difftime difftime){
        //difftime pointcut
    }

    @Around(value = "pointcut(difftime)", argNames = "joinPoint,difftime")
    public Object around(ProceedingJoinPoint joinPoint, Difftime difftime) throws Throwable {
        Object resultVal = null;
        Object[] args = joinPoint.getArgs();
        Long start = System.currentTimeMillis();
        resultVal = joinPoint.proceed(args);
        Long end = System.currentTimeMillis();
        long diffTime = end - start;
        if(diffTime > difftime.difftime()) {
            String paramsStr = null;
            if(args != null) {
                try {
                    paramsStr = JSON.toJSONString(args);
                } catch (Exception e) {
                    paramsStr = Arrays.toString(args);
                }
            }

            String resultStr = null;
            if(resultVal != null) {
                try {
                    resultStr = JSON.toJSONString(resultVal);
                } catch (Exception e) {
                    resultStr = resultVal.toString();
                }
            }

            log.warn("{} [{}], params:{}, return:{}, time:{}ms > {}ms(expect time)",difftime.desc(), joinPoint.getSignature(), paramsStr, resultStr, end-start, difftime.difftime());
        }

        return resultVal;
    }
}
