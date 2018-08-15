package com.example.util;

import com.example.exception.BaseException;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.concurrent.*;


/**
 * @title: CmdUtils
 * @Description: 执行命令行
 * @author: roverxiafan
 * @date: 2017/01/29 10:21
 */
@Slf4j
public class CmdUtils {
    private CmdUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("cmd-pool-%d").build();
    private static final ExecutorService POOL = new ThreadPoolExecutor(5, 10, 3000, TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(64), THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());


    /**
     * 运行cmd 命令行
     * @param cmd 命令行
     * @return 命令行执行结果
     * @throws IOException IOException
     */
    public static String execCmd(String cmd) throws IOException {
        return execCmd(cmd, 1000);
    }

    /**
     * 运行cmd 命令行
     * @param cmd 命令行
     * @param milliseconds 执行时间毫秒
     * @return 命令行执行结果
     * @throws IOException IOException
     */
    public static String execCmd(String cmd, long milliseconds) throws IOException {
        log.info("exec cmd : cmd={}", cmd);
        if (StringUtils.isBlank(cmd)) {
            log.error("cmd is not allowed empty");
            throw new IllegalArgumentException("cmd is empty");
        }
        Runtime r = Runtime.getRuntime();
        final Process process = r.exec(cmd);

        Future<String> future = POOL.submit(() -> {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new SequenceInputStream (process.getInputStream (), process.getErrorStream ())))) {
                String line;
                process.waitFor();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append(" ");
                }
            }
            catch (InterruptedException e) {
                log.error("exec cmd error:{}", cmd, e);
                Thread.currentThread().interrupt();
            }
            return sb.toString();
        });

        try {
            return future.get(milliseconds, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("exec cmd is not ended:{}", cmd, e);
            future.cancel(true);
            throw new BaseException("exec cmd exception");
        }
    }
}
