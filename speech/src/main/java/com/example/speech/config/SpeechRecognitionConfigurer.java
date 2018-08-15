package com.example.speech.config;


import com.baidu.aip.speech.AipSpeech;
import com.example.speech.recognition.SpeechRecognition;
import com.example.speech.recognition.baidu.BaiduSpeechRecognition;
import com.example.speech.recognition.baidu.BaiduSpeechRecognitionBuilder;
import com.example.speech.recognition.baidu.BaiduSpeechRecognitionProp;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @title: SpeechRecognitionConfigurer
 * @Description: 语音识别配置
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
@Configuration
public class SpeechRecognitionConfigurer {

    @Bean(name = "speechRecognition")
    public SpeechRecognition baiduSpeechRecognition(){
        return new BaiduSpeechRecognition(aipSpeech1(), aipSpeech2(), aipSpeech3());
    }

    @Bean
    @ConfigurationProperties(prefix = "speech-recognition.baidu.one")
    public BaiduSpeechRecognitionProp baiduSpeechRecognitionProp1() {
        return new BaiduSpeechRecognitionProp();
    }

    @Bean
    @ConfigurationProperties(prefix = "speech-recognition.baidu.two")
    public BaiduSpeechRecognitionProp baiduSpeechRecognitionProp2() {
        return new BaiduSpeechRecognitionProp();
    }

    @Bean
    @ConfigurationProperties(prefix = "speech-recognition.baidu.three")
    public BaiduSpeechRecognitionProp baiduSpeechRecognitionProp3() {
        return new BaiduSpeechRecognitionProp();
    }

    @Bean
    public AipSpeech aipSpeech1() {
        return BaiduSpeechRecognitionBuilder.create().build(baiduSpeechRecognitionProp1());
    }

    @Bean
    public AipSpeech aipSpeech2() {
        return BaiduSpeechRecognitionBuilder.create().build(baiduSpeechRecognitionProp2());
    }

    @Bean
    public AipSpeech aipSpeech3() {
        return BaiduSpeechRecognitionBuilder.create().build(baiduSpeechRecognitionProp3());
    }
}
