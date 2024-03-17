package org.bigboss.springsecuritydemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.bigboss.springsecuritydemo.model.CommonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:37
 * @description: 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<?> handleException(Exception e) {
        log.error("Exception: ", e);
        return CommonResult.failed();
    }

}
