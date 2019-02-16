package com.example.web.exception;

import com.example.exception.BaseException;
import com.example.web.result.R;
import com.example.web.result.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.dao.EmptyResultDataAccessException;

/**
 * @title: BaseExceptionHandler
 * @Description: 异常处理器
 * @author: roverxiafan
 * @date: 2016/12/16 15:37
 */
@Slf4j
public class BaseExceptionHandler {
	@ExceptionHandler(IllegalParameterException.class)
	public ResponseEntity<Object> handleIllegalParameterException(IllegalParameterException e) {
		return new ResponseEntity<>(R.error(e.getCode(), e.getMsg()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateRequestException.class)
	public ResponseEntity<Object> handleDuplicateRequestException(DuplicateRequestException e) {
		return new ResponseEntity<>(R.error(e.getCode(), e.getMsg()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(BaseException.class)
	public R handleBaseException(BaseException e) {
		return R.error(e.getCode(), e.getMsg());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
		StringBuilder errorInfo = new StringBuilder();
		for(ConstraintViolation c:e.getConstraintViolations()) {
			errorInfo.append(c.getMessage()).append(",");
		}
		if(errorInfo.length()>1) {
			errorInfo.deleteCharAt(errorInfo.length()-1);
		}
		return new ResponseEntity<>(R.error(RetCodeEnum.ILLEGAL_PARAM.val(), errorInfo.toString()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Object> handleIllegalStateException(IllegalStateException e) {
		return new ResponseEntity<>(R.error(RetCodeEnum.ILLEGAL_PARAM.val(), RetCodeEnum.ILLEGAL_PARAM.msg()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BindException.class)
	public ResponseEntity<Object> handleBindException(BindException e) {
		StringBuilder errorInfo = new StringBuilder();
		for(FieldError o:e.getFieldErrors()) {
			if("typeMismatch".equalsIgnoreCase(o.getCode())) {
				errorInfo.append(o.getField()).append(RetCodeEnum.ILLEGAL_PARAM.msg()).append(",");
			} else {
				errorInfo.append(o.getDefaultMessage()).append(",");
			}
		}
		if(errorInfo.length()>1) {
			errorInfo.deleteCharAt(errorInfo.length()-1);
		}
		return new ResponseEntity<>(R.error(RetCodeEnum.ILLEGAL_PARAM.val(), errorInfo.toString()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
		return new ResponseEntity<>(R.error(RetCodeEnum.ILLEGAL_PARAM.val(), e.getName()+RetCodeEnum.ILLEGAL_PARAM.msg()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		return new ResponseEntity<>(R.error(RetCodeEnum.ILLEGAL_PARAM.val(), e.getParameterName()+RetCodeEnum.ILLEGAL_PARAM.msg()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		return new ResponseEntity<>(R.error(RetCodeEnum.INCORRECT_HTTP_METHOD.val(), RetCodeEnum.INCORRECT_HTTP_METHOD.msg()), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		BindingResult bindingResult = e.getBindingResult();

		StringBuilder errorInfo = new StringBuilder();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorInfo.append(fieldError.getDefaultMessage()).append(",");
		}
		if(errorInfo.length()>1) {
			errorInfo.deleteCharAt(errorInfo.length()-1);
		}

		return new ResponseEntity<>(R.error(RetCodeEnum.ILLEGAL_PARAM.val(), errorInfo.toString()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return new ResponseEntity<>(R.error(RetCodeEnum.ILLEGAL_PARAM.val(), RetCodeEnum.ILLEGAL_PARAM.msg()), HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler(EmptyResultDataAccessException.class)
//	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
//		log.error(e.getMessage(), e);
//		return new ResponseEntity<>(R.error(RetCodeEnum.RESOURCE_NOT_EXIST.val(), RetCodeEnum.RESOURCE_NOT_EXIST.msg()), HttpStatus.GONE);
//	}
//
//	@ExceptionHandler(DuplicateKeyException.class)
//	public ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException e) {
//		log.error(e.getMessage(), e);
//		return new ResponseEntity<>(R.error(RetCodeEnum.DUPLICATE_KEY.val(), RetCodeEnum.DUPLICATE_KEY.msg()), HttpStatus.CONFLICT);
//	}

//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<>(R.error(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
