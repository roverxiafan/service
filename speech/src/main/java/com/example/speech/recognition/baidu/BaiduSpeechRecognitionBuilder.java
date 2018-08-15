package com.example.speech.recognition.baidu;

//import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.AipSpeech;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @title: BaiduSpeechRecognitionBuilder
 * @Description: 百度语音识别建造者
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
@Slf4j
@Configuration
public class BaiduSpeechRecognitionBuilder {
    public static BaiduSpeechRecognitionBuilder create() {
        return new BaiduSpeechRecognitionBuilder();
    }

    /**
     * 创建百度语音识别客户端
     * @param baiduSpeechRecognitionProp 百度语音识别api参数
     * @return 百度语音识别客户端
     */
    public AipSpeech build(BaiduSpeechRecognitionProp baiduSpeechRecognitionProp) {
        AipSpeech aipSpeech = new AipSpeech(baiduSpeechRecognitionProp.getAppId(), baiduSpeechRecognitionProp.getApiKey(), baiduSpeechRecognitionProp.getSecretKey());
        aipSpeech.setConnectionTimeoutInMillis(baiduSpeechRecognitionProp.getConnectionTimeout());
        aipSpeech.setSocketTimeoutInMillis(baiduSpeechRecognitionProp.getSocketTimeout());

        return aipSpeech;
    }
}
