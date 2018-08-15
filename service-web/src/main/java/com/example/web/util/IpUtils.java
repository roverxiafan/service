package com.example.web.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @title: IPUtils
 * @Description: ip
 * @author: roverxiafan
 * @date: 2017/6/19 11:19
 */
public class IpUtils {

    private IpUtils() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 获取IP地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || ip.startsWith("192.168")) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 如果运用了多级反向代理则取第一个逗号前面的ip
        int pos = ip.indexOf(",");
        if (pos > 0) {
            ip = ip.substring(0, pos);
        }

        return ip;
    }

}
