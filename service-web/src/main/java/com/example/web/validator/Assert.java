package com.example.web.validator;

import com.example.exception.BaseException;
import com.example.util.StringEx;
import com.example.web.result.RetCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @title: Assert
 * @Description: 数据校验
 * @author: roverxiafan
 * @date: 2017/6/22 17:46
 */
public abstract class Assert {

    private static Pattern MOBILE_PATTERN = Pattern.compile("^((1[0-9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    public static void isBlank(String str, int code, String message) {
        if (StringUtils.isBlank(str)) {
            fail(code, message);
        }
    }

    public static void isNull(Object object, int code,  String message) {
        if (object == null) {
            fail(code, message);
        }
    }

    public static void isUid(Object object) {
        if (!StringEx.isPositiveLong(object.toString()))  {
            fail(RetCodeEnum.INVALID_UID.val(), RetCodeEnum.INVALID_UID.msg());
        }
    }

    public static void isSid(Object object) {
        if (!StringUtils.isNumeric(object.toString()))  {
            fail(RetCodeEnum.INVALID_SID.val(), RetCodeEnum.INVALID_SID.msg());
        }
    }

    public static void isMobile(String mobile) {
        Assert.isNull(mobile, RetCodeEnum.INVALID_MOBILE.val(), RetCodeEnum.INVALID_MOBILE.msg());
        Matcher m = MOBILE_PATTERN.matcher(mobile);
        if (!m.matches()) {
            fail(RetCodeEnum.INVALID_MOBILE.val(), RetCodeEnum.INVALID_MOBILE.msg());
        }
    }

    public static void isEmail(String email) {
        Assert.isNull(email, RetCodeEnum.INVALID_EMAIL.val(), RetCodeEnum.INVALID_EMAIL.msg());
        String str = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            fail(RetCodeEnum.INVALID_EMAIL.val(), RetCodeEnum.INVALID_EMAIL.msg());
        }
    }


    private static void fail() {
            throw new BaseException(RetCodeEnum.ILLEGAL_PARAM.val(), RetCodeEnum.ILLEGAL_PARAM.msg());
    }

    private static void fail(int code, String message) {
        throw new BaseException(code, message);
    }
}
