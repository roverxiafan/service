package com.example.speech.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.util.StringEx;
import com.example.web.exception.DuplicateRequestException;
import com.example.web.result.R;
import com.example.web.common.upload.FileInfo;
import com.example.speech.config.SpeechFileUpload;
import com.example.speech.config.SpeechUploadProp;
import com.example.speech.util.SpeechUtil;
import com.example.speech.service.RecognizeService;
import com.example.speech.recognition.SpeechRecognition;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @title: RecognizeController
 * @Description: 语音识别接口
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
@Slf4j
@RestController
@RequestMapping(value = "/recognize")
public class RecognizeController {
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
     * 匹配标准文本与语音识别文本相似度
     * 使用redisson分布式锁防止重复提交
     * @param file mp3文件
     * @param uid 用户id
     * @param sourceText 语音文本
     * @return 识别文本与语音文本相似度
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    @PostMapping
    public R recognizeSpeech(@RequestParam("file") MultipartFile file, @RequestParam("uid") long uid,
                             @RequestParam("sourceText") String sourceText) throws IOException, InterruptedException {
        RLock rLock = redissonClient.getLock(String.format("lock.speech.recognize.%s", uid));
        if(!rLock.tryLock(0, 2, TimeUnit.SECONDS)) {
            throw new DuplicateRequestException();
        }

        FileInfo fileInfo = fileUpload.upload(file, uploadProp.getAllowExtensions());
        log.info(JSONObject.toJSONString(fileInfo));
        String pcmPath = String.format("%s%s%s", uploadProp.getTmpPath(), File.separator, fileInfo.getFileName().replace("."+fileInfo.getExtName(), ".pcm"));
        log.info(pcmPath);

        SpeechUtil.convertMp3ToPcm(fileInfo.getFilePath(), pcmPath);

        try {
            String recognitionText = speechRecognition.recognizeSpeech(pcmPath);
            double similar = StringEx.stringSimilarity(sourceText, recognitionText);
            return R.ok().put(fileInfo).put("recognitionText", recognitionText).put("similar", similar);
        } finally {
            try{
                rLock.unlock();
            } catch (Exception e) {
                log.debug("rLock unlock", e);
            }
            recognizeService.deleteTmpFile(pcmPath);
        }
    }
}
