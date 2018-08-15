package com.example.encrypt.spring.boot.autoconfigure.filter;

import com.example.encrypt.spring.boot.autoconfigure.http.EncryptionRequestWrapper;
import com.example.encrypt.spring.boot.autoconfigure.http.EncryptionResponseWrapper;
import com.example.encrypt.spring.boot.autoconfigure.properties.EncryptProperties;
import com.example.util.encrypt.AES;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: EncryptFilter
 * @Description: 加解密filter
 * @author: roverxiafan
 * @date: 2017/6/27 15:13
 */
@Slf4j
public class EncryptionFilter extends OncePerRequestFilter {
    private static final String DEFAULT_ENCRYPT_KEY = "d9hQstZ95yri9usa";
    @Autowired
    private EncryptProperties encryptProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String f = request.getParameter("f");
        String q = request.getParameter("q");

        if(StringUtils.isNotBlank(f) && StringUtils.isNotBlank(q)) {
            try {
                Map<String, String> qMap = parseEncryptionParams(AES.decrypt(URLDecoder.decode(q, request.getCharacterEncoding()).replace(" ", "+"), getSecretKey(f)));
                if(qMap != null && StringUtils.isNotBlank(qMap.get("sk"))) {
                    Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());
                    for (Map.Entry<String, String> e : qMap.entrySet()) {
                        parameterMap.put(e.getKey(), new String[]{e.getValue()});
                    }
                    EncryptionRequestWrapper encryptionRequestWrapper = new EncryptionRequestWrapper(request, parameterMap);
                    EncryptionResponseWrapper encryptionResponseWrapper = new EncryptionResponseWrapper(response);
                    encryptionRequestWrapper.setAttribute("encrypt", true);
                    super.doFilter(encryptionRequestWrapper, encryptionResponseWrapper, filterChain);
                    byte[] data = encryptionResponseWrapper.getResponseData();
                    log.debug("原始返回数据： " + new String(data, request.getCharacterEncoding()));
                    String responseBodyMw = AES.encrypt(new String(data, request.getCharacterEncoding()), qMap.get("sk"));
                    log.debug("加密返回数据： " + responseBodyMw);
                    writeResponse(response, responseBodyMw);
                } else {
                    super.doFilter(request, response, filterChain);
                }
            } catch (Exception e) {
                super.doFilter(request, response, filterChain);
            }
        } else {
            super.doFilter(request, response, filterChain);
        }
    }

    private void writeResponse(ServletResponse response, String responseString)
            throws IOException {
        PrintWriter out = response.getWriter();
        out.print(responseString);
        out.flush();
        out.close();
    }


    private String getRequestBody(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getReader());
        } catch (IOException e) {
            log.error("read request error", e);
        }
        return null;
    }

    private Map<String, String> parseEncryptionParams(String content) {
        if(StringUtils.isBlank(content)) {
            return null;
        }
        String[] arr = content.split("&");
        Map<String, String> map = new HashMap<>();
        for (String params : arr) {
            if (StringUtils.isNotBlank(params)) {
                if (params.contains("=")) {
                    String key = params.substring(0, params.indexOf("="));
                    String value = params.substring(key.length() + 1);
                    map.put(key, value);
                } else {
                    map.put(params, "");
                }
            }
        }
        return map;
    }


    private String getSecretKey(String f) {
        if (encryptProperties==null || encryptProperties.getEncryptions() == null ||
                StringUtils.isBlank(encryptProperties.getEncryptions().get(f))) {
            return DEFAULT_ENCRYPT_KEY;
        }

        return encryptProperties.getEncryptions().get(f);
    }
}
