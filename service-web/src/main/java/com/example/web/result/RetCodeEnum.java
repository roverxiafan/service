package com.example.web.result;

/**
 * @title: R
 * @Description: 返回结果枚举值
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
public enum RetCodeEnum {
    OK(0, "请求成功"),
    INCORRECT_HTTP_METHOD(405, "错误的http请求类型"),
    DUPLICATE_REQUEST(409, "请求重复，请勿重复提交"),
    ERROR(500, "系统错误，请重试"),
    DUPLICATE_KEY(501, "数据重复"),
    RESOURCE_NOT_EXIST(502, "请求资源不存在"),
    ILLEGAL_PARAM(1000, "参数错误"),
    INVALID_UID(1001, "用户id无效"),
    INVALID_MOBILE(1002, "手机号码无效"),
    INVALID_EMAIL(1003, "邮箱地址无效"),
    EMPTY_FILE(1004, "文件为空"),
    ILLEGAL_FILE_TYPE(1005, "文件类型不符合规则"),
    ILLEGAL_KEYWORD(1006, "不符合规范的字符或词语"),
    ENCRYPT_ERROR(2001, "参数加密错误"),
    DECRYPT_ERROR(2002, "参数解密错误"),
    INVALID_SID(10000, "用户未登录");

    private final int val;
    private final String msg;

    RetCodeEnum(int val, String msg) {
        this.val = val;
        this.msg = msg;
    }

    public int val() {
        return this.val;
    }

    public String msg() {
        return this.msg;
    }

}
