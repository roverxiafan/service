package com.example.web.common.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @title: UploadProp
 * @Description: 上传文件配置
 * @author: roverxiafan
 * @date: 2017/3/16 17:16
 */
@Data
@ConfigurationProperties(prefix = "upload")
public class UploadProp {
    private String forbiddenExtensions;
    private String allowExtensions;
    private String basePath;
    private String baseUrl;
    private String tmpPath;
}

