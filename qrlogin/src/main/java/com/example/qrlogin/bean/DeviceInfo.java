package com.example.qrlogin.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @title: DeviceInfo
 * @Description: 设备信息
 * @author: roverxiafan
 * @date: 2017/01/29 10:35
 */
@Data
public class DeviceInfo {
    private static final String DEFAULT_VALUE = "unknown";
    @NotNull
    @Length(min = 1)
    private String f = DEFAULT_VALUE;

    @NotBlank(message = "deviceID为空")
    private String deviceID = DEFAULT_VALUE;

    @NotBlank(message = "deviceName为空")
    private String deviceName = DEFAULT_VALUE;

    @NotBlank(message = "deviceType为空")
    private String deviceType = DEFAULT_VALUE;

    @NotBlank(message = "deviceResolution为空")
    private String deviceResolution = DEFAULT_VALUE;

    @NotBlank(message = "version为空")
    private String version = DEFAULT_VALUE;

    @NotBlank(message = "channel为空")
    private String channel = DEFAULT_VALUE;

    private String ip = "127.0.0.1";
}
