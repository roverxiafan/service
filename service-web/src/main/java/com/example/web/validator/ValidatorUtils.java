package com.example.web.validator;

import com.example.exception.BaseException;
import com.example.web.result.RetCodeEnum;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @title: ValidatorUtils
 * @Description: 参数校验
 * @author: roverxiafan
 * @date: 2018/6/22 17:46
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws BaseException  校验不通过，则报BaseException异常
     */
    public static void validate(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new BaseException(RetCodeEnum.ILLEGAL_PARAM.val(), constraint.getMessage());
        }
    }
}
