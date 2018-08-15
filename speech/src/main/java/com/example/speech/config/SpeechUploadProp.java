package com.example.speech.config;


import com.example.web.common.upload.UploadProp;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @title: SpeechUploadProp
 * @Description: 上传属性配置
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
@ConfigurationProperties(prefix = "upload")
@Component
public class SpeechUploadProp extends UploadProp {
}
