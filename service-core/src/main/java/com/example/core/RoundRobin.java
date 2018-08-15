package com.example.core;

/**
 * @title: RoundRobin
 * @Description: 轮询获取bean
 * @author: roverxiafan
 * @date: 2017/6/7 16:07
 */
public interface RoundRobin<T> {
    /**
     * 轮询获取bean
     * @return bean
     */
    default T roundRobin(){
        return null;
    }
}
