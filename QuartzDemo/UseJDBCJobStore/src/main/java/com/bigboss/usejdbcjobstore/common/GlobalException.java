package com.bigboss.usejdbcjobstore.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author: maifuwa
 * @date: 2024/10/8 9:52
 * @description: 全局异常
 */
@Getter
public class GlobalException extends RuntimeException {

    private final int failedCode;
    private final String failedMessage;

    public GlobalException(String failedMessage) {
        this(HttpStatus.INTERNAL_SERVER_ERROR.value(), failedMessage);
    }

    public GlobalException(int failedCode, String failedMessage) {
        super(failedMessage);
        this.failedCode = failedCode;
        this.failedMessage = failedMessage;
    }

    public GlobalException(String failedMessage, Throwable cause) {
        this(HttpStatus.INTERNAL_SERVER_ERROR.value(), failedMessage, cause);
    }

    public GlobalException(int failedCode, String failedMessage, Throwable cause) {
        super(cause);
        this.failedCode = failedCode;
        this.failedMessage = failedMessage;
    }

    public static GlobalException internalServerError(String message, Throwable cause) {
        return new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, cause);
    }

    public static GlobalException badRequest(String message, Throwable cause) {
        return new GlobalException(HttpStatus.BAD_REQUEST.value(), message, cause);
    }

    public static GlobalException notFound(String message, Throwable cause) {
        return new GlobalException(HttpStatus.NOT_FOUND.value(), message, cause);
    }
}