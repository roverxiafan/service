package com.example.encrypt.spring.boot.autoconfigure;

import com.example.encrypt.spring.boot.autoconfigure.filter.EncryptionFilter;
import com.example.encrypt.spring.boot.autoconfigure.properties.EncryptProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @title: EncryptAutoConfigure
 * @Description: springboot加解密自动配置
 * @author: roverxiafan
 * @date: 2017/9/28 15:07
 */
@Configuration
@Component
@ConditionalOnClass(EncryptionFilter.class)
@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptAutoConfigure {
    @Bean(name = "encryptionFilter")
    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "encrypt", havingValue = "true")
    public EncryptionFilter initEncryptFilter (){
        return  new EncryptionFilter();
    }
}
