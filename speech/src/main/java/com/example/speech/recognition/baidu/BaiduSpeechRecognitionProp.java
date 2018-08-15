package com.example.speech.recognition.baidu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @title: BaiduSpeechRecognitionProp
 * @Description: 百度语音识别api参数
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
@Data
@ConfigurationProperties(prefix = "speech-recognition.baidu")
public class BaiduSpeechRecognitionProp {
    private String appId;
    private String apiKey;
    private String secretKey;
    private int connectionTimeout;
    private int socketTimeout;
}
