package com.example.web.exception;

import com.example.exception.BaseException;
import com.example.web.result.RetCodeEnum;

/**
 * @title: DuplicateRequestException
 * @Description: 重复请求异常
 * @author: roverxiafan
 * @date: 2016/12/16 17:16
 */
public class DuplicateRequestException extends BaseException {

    private static final long serialVersionUID = 5459083809270370L;

    public DuplicateRequestException() {
        super(RetCodeEnum.DUPLICATE_REQUEST.val(), RetCodeEnum.DUPLICATE_REQUEST.msg());
    }

    public DuplicateRequestException(String message) {
        super(RetCodeEnum.DUPLICATE_REQUEST.val(), message);
    }

    public DuplicateRequestException(String message, Throwable cause) {
        super(RetCodeEnum.DUPLICATE_REQUEST.val(), message, cause);
    }

    public DuplicateRequestException(int code, String message) {
        super(code, message);
    }

    public DuplicateRequestException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
