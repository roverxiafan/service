package com.example.encrypt.spring.boot.autoconfigure.http;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * @title: EncryptionResponseWrapper
 * @Description: 加解密response
 * @author: roverxiafan
 * @date: 2017/6/27 15:15
 */
public class EncryptionResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream buffer = null;
    private ServletOutputStream out = null;
    private PrintWriter writer = null;
    public EncryptionResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        buffer = new ByteArrayOutputStream();// 真正存储数据的流
        out = new SecurityOutputStreamWrapper(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
    }

    /** 重载父类获取outputstream的方法 */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    /** 重载父类获取writer的方法 */
    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException {
        return writer;
    }

    /** 重载父类获取flushBuffer的方法 */
    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    /** 将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据 */
    public byte[] getResponseData() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

    /** 内部类，对ServletOutputStream进行包装 */
    private class SecurityOutputStreamWrapper extends ServletOutputStream {
        private ByteArrayOutputStream bos = null;

        public SecurityOutputStreamWrapper(ByteArrayOutputStream stream) throws IOException {
            bos = stream;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            bos.write(b, 0, b.length);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}
