package com.example.web.exception;

import com.example.exception.BaseException;
import com.example.web.result.RetCodeEnum;


/**
 * @title: IllegalParameterException
 * @Description: 参数错误异常
 * @author: roverxiafan
 * @date: 2016/12/16 17:16
 */
public class IllegalParameterException extends BaseException {


    private static final long serialVersionUID = -5549483446104209979L;

    public IllegalParameterException() {
        super(RetCodeEnum.ILLEGAL_PARAM.val(), RetCodeEnum.ILLEGAL_PARAM.msg());
    }

    public IllegalParameterException(String message) {
        super(RetCodeEnum.ILLEGAL_PARAM.val(), message);
    }

    public IllegalParameterException(String message, Throwable cause) {
        super(RetCodeEnum.ILLEGAL_PARAM.val(), message, cause);
    }

    public IllegalParameterException(int code, String message) {
        super(code, message);
    }

    public IllegalParameterException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
