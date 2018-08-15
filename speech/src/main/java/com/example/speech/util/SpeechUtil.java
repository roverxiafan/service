package com.example.speech.util;


import com.example.common.aop.difftime.Difftime;
import com.example.exception.BaseException;
import com.example.util.CmdUtils;
import com.example.web.result.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;


/**
 * @title: SpeechUtil
 * @Description: 语音文件处理工具类
 * @author: roverxiafan
 * @date: 2018/2/7 09:45
 */
@Slf4j
public class SpeechUtil {
	private SpeechUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 语音文件转换为pcm
	 * @param srcFile 源文件
	 * @param descFile 目标文件
	 * @throws IOException IOException
	 */
	@Difftime(difftime = 500, desc = "convert mp3 to pcm")
	public static void convertMp3ToPcm(String srcFile, String descFile) throws IOException {
		// 截取文件前5秒 ffmpeg -y -i %s -acodec pcm_s16le -f s16le -ac 1 -ar 8000 -ss 00:00:00 -t 00:00:05 %s", srcFile, descFile
		String s = CmdUtils.execCmd(String.format("ffmpeg -y -i %s -acodec pcm_s16le -f s16le -ac 1 -ar 8000 %s", srcFile, descFile));
		log.info("convert mp3 to pcm:{}", s);
		if(!new File(descFile).exists()) {
			throw new BaseException(RetCodeEnum.ERROR.val(), RetCodeEnum.ERROR.msg());
		}
	}

}
