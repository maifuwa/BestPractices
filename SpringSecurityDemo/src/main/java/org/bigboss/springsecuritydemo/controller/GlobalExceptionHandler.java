package org.bigboss.springsecuritydemo.controller;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.bigboss.springsecuritydemo.controller.exception.MemberException;
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

    @ExceptionHandler(MemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<?> handleException(MemberException e) {
        return CommonResult.failed(400, e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<?> handleExpiredJwtException(JwtException e) {
        return CommonResult.failed(400, e.getMessage());
    }
}
