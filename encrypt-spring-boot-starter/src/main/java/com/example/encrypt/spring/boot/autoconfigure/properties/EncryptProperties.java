package com.example.encrypt.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: EncryptProperties
 * @Description: 加解密密钥配置
 * @author: roverxiafan
 * @date: 2017/9/28 15:07
 */
@Data
@Component
@ConfigurationProperties("encrypt")
public class EncryptProperties {
    private Map<String, String> encryptions = new HashMap<>();
}
