package com.example.encrypt.spring.boot.autoconfigure.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * @title: EncryptionRequestWrapper
 * @Description: 加解密request
 * @author: roverxiafan
 * @date: 2017/6/27 15:15
 */
public class EncryptionRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String[]> parameterMap;

    public EncryptionRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public EncryptionRequestWrapper(HttpServletRequest request, Map<String, String[]> parameterMap) {
        super(request);
        this.parameterMap=parameterMap;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.parameterMap.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }

    @Override
    public String getParameter(String name) {
        String[] v = parameterMap.get(name);
        if(v == null || v.length==0) {
            return null;
        }
        return v[0];
    }
}
