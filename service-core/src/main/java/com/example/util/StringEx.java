package com.example.util;

import org.apache.commons.text.similarity.JaccardSimilarity;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: StringEx
 * @Description: 字符串工具类
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
public class StringEx {
    private StringEx() {
        throw new IllegalStateException("Utility class");
    }

    public static final int INT_MAX_LENGTH = Integer.toString(Integer.MAX_VALUE).length();
    public static final int LONG_MAX_LENGTH = Long.toString(Long.MAX_VALUE).length();
    public static final String DIGIT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final Pattern MOBILE_PATTERN = Pattern.compile("^(1[0-9][0-9])\\d{8}$");
    public static final Pattern MAIL_PATTERN = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    public static String clean(String str) {
        return str == null?"":str.trim();
    }

    public static boolean isNumeric(String s) {
        if(s != null && s.trim().length() != 0) {
            if(s.charAt(0) == 45) {
                s = s.substring(1);
            }

            int i = 0;

            for(int n = s.length(); i < n; ++i) {
                char c = s.charAt(i);
                if(!Character.isDigit(c) && c != 46) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isInt(String s) {
        if(isEmpty(s)) {
            return false;
        }

        if(s.charAt(0) == 45) {
            if(s.length() > INT_MAX_LENGTH+1) {
                return false;
            }
        }  else {
            if(s.length() > INT_MAX_LENGTH) {
                return false;
            }
        }

        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isNaturalInt(String s) {
        if(!isInt(s)) {
            return false;
        }

        return Integer.parseInt(s) >= 0;
    }

    public static boolean isPositiveInt(String s) {
        if(!isNaturalInt(s)) {
            return false;
        }

        return Integer.parseInt(s) >= 1;
    }


    public static boolean isLong(String s) {
        if(isEmpty(s)) {
            return false;
        }

        if(s.charAt(0) == 45) {
            if(s.length() > LONG_MAX_LENGTH+1) {
                return false;
            }
        }  else {
            if(s.length() > LONG_MAX_LENGTH) {
                return false;
            }
        }

        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static boolean isNaturalLong(String s) {
        if(!isLong(s)) {
            return false;
        }

        return Long.parseLong(s) >= 0L;
    }

    public static boolean isPositiveLong(String s) {
        if(!isNaturalLong(s)) {
            return false;
        }

        return Long.parseLong(s) >= 1L;
    }

    public static boolean isFloat(String s) {
        if(isEmpty(s)) {
            return false;
        } else {
            if(s.contains(".")) {
                int dotPos = s.indexOf(".");
                s = s.substring(0, dotPos) + s.substring(dotPos + 1);
            }

            return isNumeric(s);
        }
    }

    public static String getString(Object obj, String replace) {
        if(obj == null) {
            return replace;
        } else {
            String result = obj.toString();
            return result.length() == 0?replace:result;
        }
    }

    public static String getString(String s) {
        return getString(s, "");
    }

    public static int getInt(String s, int iReplace) {
        if(s == null) {
            return iReplace;
        } else {
            if(!isEmpty(s) && s.contains(".")) {
                s = s.substring(0, s.indexOf("."));
            }

            return !isInt(s)?iReplace: Integer.parseInt(s);
        }
    }

    public static long getLong(String s, long iReplace) {
        if(s == null) {
            return iReplace;
        } else {
            if(!isEmpty(s) && s.contains(".")) {
                s = s.substring(0, s.indexOf("."));
            }

            return !isLong(s)?iReplace: Long.parseLong(s);
        }
    }

    public static float getFloat(String sCheck, float nReplace) {
        return !isFloat(sCheck)?nReplace: Float.parseFloat(sCheck);
    }

    public static String trim(String s) {
        return s == null?null:s.trim();
    }

    public static int getLength(String s) {
        if(s != null && s.length() != 0) {
            int nRet = 0;

            for(int i = 0; i < s.length(); ++i) {
                if(s.charAt(i) > 0 && s.charAt(i) < 128) {
                    ++nRet;
                } else {
                    nRet += 2;
                }
            }

            return nRet;
        } else {
            return 0;
        }
    }

    /**
     * 十进制数字转换成62进制数字
     * @param num 目标数字
     * @param radix 进制基数
     * @return 转换进制后的字符串
     */
    public static String radixConverter(long num, int radix) {
        String result = "";
        if (num == 0) {
            return "";
        } else {
            result = radixConverter(num / radix, radix);
            return result + DIGIT.charAt(new Long(num % radix).intValue());
        }
    }

    public static String randomToken() {
        return randomToken(62);
    }

    public static String randomTokenIgnoreCase() {
        return randomToken(36);
    }

    public static String randomToken(int radix) {
        Long sysTime = System.currentTimeMillis()*1000 + new Random().nextInt(1000);
        Long newNum = Long.valueOf(new StringBuilder(String.valueOf(sysTime)).reverse().toString());
        return StringEx.radixConverter(newNum, radix);
    }

    /**
     * 计算字符串相似度
     * @param str1 字符串
     * @param str2 字符串
     * @return 相似度
     */
    public static double stringSimilarity(String str1, String str2) {
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        return jaccardSimilarity.apply(str1, str2);
    }

    /**
     * 验证手机号码
     */
    public static boolean isValidMobile(String mobile) {
        if (mobile == null) {
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(mobile);
        return m.matches();
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher m = MAIL_PATTERN.matcher(email);
        return m.matches();
    }
}
