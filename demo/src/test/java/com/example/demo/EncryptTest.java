package com.example.demo;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.util.encrypt.AES;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class EncryptTest {
    private static RestTemplate restTemplate;
    static {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(3000);
        factory.setConnectTimeout(3000);
        restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.getMessageConverters().set(2, new FastJsonHttpMessageConverter());
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return clientHttpResponse.getStatusCode() != HttpStatus.OK;
            }
            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                System.err.println("HTTP Status Code:"+clientHttpResponse.getStatusCode());
            }
        };
        restTemplate.setErrorHandler(responseErrorHandler);
    }

    /**
     * 请求加解密测试
     */
    @Test
    public void testEncrypt() throws UnsupportedEncodingException {
        String str = "value=" + URLEncoder.encode("测试生成+&#测试", "utf-8") + "&sk=12345678";
        String q = AES.encrypt(str, "VFMeI^dM#I%69e5B");
        System.out.println(q);
        String url = "http://localhost:8080/demo/encrypt/ciphertext?f=web&q="+URLEncoder.encode(q, "utf-8");
        System.out.println(url);
        String res = restTemplate.getForObject(url, String.class);
        String data = AES.decrypt(res, "12345678");
        System.out.println(data);
    }

    @Test
    public void testPlaintext() throws Exception {
        String value = URLEncoder.encode("测试生成+&#测试", "utf-8");
        String url = "http://localhost:8080/demo/encrypt/plaintext?value="+value;
        System.out.println(url);
        String res = restTemplate.getForObject(url, String.class);
        System.out.println(res);
    }
}
