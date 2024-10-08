package com.bigboss.usedjdbcjobstore.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/8 9:52
 */
@Getter
public class GlobalException extends RuntimeException {

    private final int failedCode;

    public GlobalException(String message) {
        super(message);
        this.failedCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public GlobalException(int failedCode, String message) {
        super(message);
        this.failedCode = failedCode;
    }

}
