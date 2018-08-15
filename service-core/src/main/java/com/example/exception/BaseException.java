package com.example.exception;

/**
 * @title: BaseException
 * @Description: BaseException
 * @author: roverxiafan
 * @date: 2016/11/15 11:29
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -7914424917222364396L;

    private int code = 500;
    private String msg = "internal server error";

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        this.msg = message;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
