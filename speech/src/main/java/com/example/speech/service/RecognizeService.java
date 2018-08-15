package com.example.speech.service;

import com.example.exception.BaseException;
import com.example.util.StringEx;
import com.example.web.result.RetCodeEnum;
import com.example.speech.config.SpeechFileUpload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @title: RecognizeService
 * @Description: 语音识别service
 * @author: roverxiafan
 * @date: 2018/2/7 09:38
 */
@Slf4j
@Service
public class RecognizeService {
    private static final int BUFF_LEN = 1024;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SpeechFileUpload fileUpload;


    /**
     * 下载文件
     * @param url 文件uri
     * @param resourcePath 文件物理地址
     * @throws IOException exception
     */
    public void downloadResource(String url, String resourcePath) throws IOException {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        if(response.getStatusCodeValue() != HttpStatus.OK.value() || response.getBody()==null) {
            throw new BaseException(RetCodeEnum.RESOURCE_NOT_EXIST.val(), RetCodeEnum.RESOURCE_NOT_EXIST.msg());
        }
        try(InputStream inputStream = new ByteArrayInputStream(response.getBody());
        OutputStream outputStream = new FileOutputStream(new File(resourcePath))) {
            File file = new File(resourcePath);
            if(!file.exists() && !file.createNewFile()) {
                throw new BaseException(RetCodeEnum.ERROR.val(), RetCodeEnum.ERROR.msg());
            }

            int len;
            byte[] buf = new byte[BUFF_LEN];
            while ((len = inputStream.read(buf, 0, BUFF_LEN)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        }
    }

    /**
     * http输出文件
     * @param response response
     * @param resourcePath 文件物理路径
     * @throws IOException IOException
     */
    public void outputResource(HttpServletResponse response, String resourcePath) throws IOException {
        try(
                OutputStream outputStream = response.getOutputStream();
                FileInputStream inputStream = new FileInputStream(resourcePath)
        ) {
            int len;
            byte[] buf = new byte[BUFF_LEN];
            while ((len = inputStream.read(buf, 0, BUFF_LEN)) != -1){
                outputStream.write(buf,0,len);
            }
            outputStream.flush();
        }
    }

    /**
     * 异步删除临时文件
     * @param tmpFiles 临时文件物理路径
     */
    @Async
    public void deleteTmpFile(String... tmpFiles) {
        if(tmpFiles == null || tmpFiles.length == 0) {
            return;
        }
        for(String s:tmpFiles) {
            fileUpload.delete(s);
        }
    }
}
