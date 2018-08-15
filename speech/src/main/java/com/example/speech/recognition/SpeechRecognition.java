package com.example.speech.recognition;

/**
 * @title: SpeechRecognize
 * @Description: 语音识别
 * @author: roverxiafan
 * @date: 2018/2/7 15:07
 */
public interface SpeechRecognition {
    /**
     * 语音识别
     * @param filePath 音频文件物理路径
     * @return 语音内容
     */
    default String recognizeSpeech(String filePath) {
        return null;
    }
}
