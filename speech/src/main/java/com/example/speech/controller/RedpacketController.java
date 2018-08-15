package com.example.speech.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.speech.config.SpeechFileUpload;
import com.example.speech.config.SpeechUploadProp;
import com.example.speech.recognition.SpeechRecognition;
import com.example.speech.service.RecognizeService;
import com.example.speech.util.SpeechUtil;
import com.example.web.common.upload.FileInfo;
import com.example.web.exception.DuplicateRequestException;
import com.example.web.result.R;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @title: RecognizeController
 * @Description: 语音识别接口
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/redpacket")
public class RedpacketController {
    @Resource
    private RecognizeService recognizeService;

    @Resource
    private SpeechRecognition speechRecognition;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private SpeechUploadProp uploadProp;

    @Resource
    private SpeechFileUpload fileUpload;


    /**
     * 简单的红包生成
     * @param money 金额
     * @param num 红包个数
     * @param request
     * @return
     */
    @RequestMapping(value = "/random")
    public R randomRedpacketsDemo(@RequestParam(name = "money", required = false, defaultValue = "20") @Digits(integer = 3,fraction=2) @DecimalMax(value = "200") @DecimalMin(value = "0.01") BigDecimal money,
                             @RequestParam(name = "num", required = false,defaultValue = "10") @Range(min = 1, max = 100) int num,
                             HttpServletRequest request) {
        int redpacketAmount = money.multiply(new BigDecimal("100")).intValue();
        if(redpacketAmount < num) {
            return R.error(-1, "每个红包最少0.01元");
        }
        List<BigDecimal> list = randomRedpackets(redpacketAmount, num);

        return R.ok().put("list", list);
    }

    private List<BigDecimal> randomRedpackets(int totalCents, int num) {
        List<BigDecimal> amountList = new ArrayList<>();
        int restMoney = totalCents;
        int restNum = num;
        Random random = new Random();
        for (int i = 0; i < num - 1; i++) {
            int amount = random.nextInt(restMoney / restNum * 2 - 1) + 1;
            restMoney -= amount;
            restNum--;
            amountList.add(new BigDecimal(amount).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_FLOOR));
        }
        amountList.add(new BigDecimal(restMoney).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_FLOOR));

        return amountList;
    }
}
