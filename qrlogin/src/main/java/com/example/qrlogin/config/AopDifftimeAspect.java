package com.example.qrlogin.config;

import com.example.common.aop.difftime.DifftimeAspect;
import org.springframework.stereotype.Component;

/**
 * @title: AopDifftimeAspect
 * @Description: 方法执行时间大于期望时间打印警告日志
 * @author: roverxiafan
 * @date: 2016/11/15 11:29
 */
@Component
public class AopDifftimeAspect extends DifftimeAspect {

}
