package com.bigboss.mybatisflexdemo.common;

import org.springframework.http.HttpStatus;

/**
 * @author: maifuwa
 * @date: 2024/10/7 10:56
 * @description: 通用返回对象
 */
public record CommonResult<T>(int code, String message, T data) {

    public static <T> CommonResult<T> success() {
        return CommonResult.success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(HttpStatus.OK.value(), "请求成功", data);
    }

    public static <T> CommonResult<T> failed() {
        return CommonResult.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), "请求失败,请稍后再试", null);
    }

    public static <T> CommonResult<T> failed(String message) {
        return CommonResult.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static <T> CommonResult<T> failed(int code, String message) {
        return CommonResult.failed(code, message, null);
    }

    public static <T> CommonResult<T> failed(int code, String message, T data) {
        return new CommonResult<>(code, message, data);
    }

    public static <T> CommonResult<T> accessDenied() {
        return CommonResult.failed(HttpStatus.FORBIDDEN.value(), "没有相关权限", null);
    }
}