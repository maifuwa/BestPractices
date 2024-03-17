package org.bigboss.springsecuritydemo.model;

/**
 * @author: maifuwa
 * @date: 2024/3/17 下午7:41
 * @description: 统一响应格式
 */
public record CommonResult<T>(int code, String message, T data) {

    public static <T> CommonResult<T> success() {
        return CommonResult.success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(200, "请求成功", data);
    }

    public static <T> CommonResult<T> failed() {
        return CommonResult.failed(500, "请求失败,请稍后再试", null);
    }

    public static <T> CommonResult<T> failed(String message) {
        return CommonResult.failed(500, message, null);
    }

    public static <T> CommonResult<T> failed(int code, String message, T data) {
        return new CommonResult<>(code, message, data);
    }

    public static <T> CommonResult<T> unauthorized() {
        return CommonResult.failed(401, "登录失败或token已经过期", null);
    }

    public static <T> CommonResult<T> accessDenied() {
        return CommonResult.failed(403, "没有相关权限", null);
    }
}
