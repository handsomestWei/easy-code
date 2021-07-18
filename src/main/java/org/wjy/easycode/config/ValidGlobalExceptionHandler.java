package org.wjy.easycode.config;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 捕获未处理的valid全局异常
 */
@ControllerAdvice
public class ValidGlobalExceptionHandler {

    /**
     * 处理@RequestBody参数校验错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return "MethodArgumentNotValidException " + message;
    }

    /**
     * 处理Get请求参数校验错误
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public String BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return "BindException " + message;
    }

    /**
     * 处理@RequestParam参数校验错误
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public String ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return "ConstraintViolationException " + message;
    }
}
