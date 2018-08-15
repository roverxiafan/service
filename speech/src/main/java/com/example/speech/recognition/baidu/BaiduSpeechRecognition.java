package com.example.speech.recognition.baidu;

import com.baidu.aip.speech.AipSpeech;
import com.example.core.RoundRobin;
import com.example.exception.BaseException;
import com.example.speech.recognition.SpeechRecognition;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @title: BaiduSpeechRecognition
 * @Description: 百度语音识别，每个帐号每天50000次，采用三个帐号轮询策略
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
@Slf4j
public class BaiduSpeechRecognition implements SpeechRecognition, RoundRobin<AipSpeech> {
    private AipSpeech[] aipSpeeches;

    public BaiduSpeechRecognition(AipSpeech... aipSpeech) {
        aipSpeeches = aipSpeech;
    }

    private AtomicInteger roundRobinNum = new AtomicInteger();

    @Resource
    private RedissonClient redissonClient;

    private static final String AUDIO_FORMAT = "pcm";
    private static final int AUDIO_RATE = 8000;
    private static final String RETURN_CODE = "err_no";
    private static final String RETURN_CONTENT = "result";

    /**
     * 百度语音识别
     * redisson实现分布式信号量限制并发请求百度语音识别api数量
     * @param filePath pcm文件物理地址
     * @return 语音文字
     */
    @Override
    public String recognizeSpeech(String filePath) {
        RPermitExpirableSemaphore semaphore = redissonClient.getPermitExpirableSemaphore("semaphore.speech.recognize");
        semaphore.trySetPermits(300);
        String permitId = null;
        try {
            permitId = semaphore.tryAcquire(1000, 8000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("get semaphore exception", e);
        }

        if (permitId == null) {
            throw new BaseException("请求人数过多请稍后再试");
        }

        log.info("permitid:{}", permitId);
        try {
            StringBuilder sb = new StringBuilder();
            JSONObject res = roundRobin().asr(filePath, AUDIO_FORMAT, AUDIO_RATE, null);
            log.debug("{}:{}", filePath, res);
            if (res.getInt(RETURN_CODE) == 0) {
                for (int i = 0; i < res.getJSONArray(RETURN_CONTENT).length(); i++) {
                    sb.append(res.getJSONArray(RETURN_CONTENT).getString(i));
                }
            }
            return sb.toString();
        } finally {
            try {
                semaphore.release(permitId);
            } catch (Exception e) {
                log.info("release semaphore exception", e);
            }
        }
    }


    /**
     * 轮询选择百度语音识别客户端
     * @return aipSpeech
     */
    @Override
    public AipSpeech roundRobin() {
        int count = roundRobinNum.getAndIncrement();
        if(count >= Integer.MAX_VALUE) {
            roundRobinNum.set(0);
        }
        int clientNum = count % aipSpeeches.length;
        log.debug("aipSpeeches roundRobin count:{}, aipSpeech:{}", count, clientNum);
        return aipSpeeches[clientNum];
    }
}
