package com.bigboss.useramjobstore.common;

import lombok.Getter;

/**
 * @author: maifuwa
 * @date: 2024/10/14 20:51
 * @description:
 */
@Getter
public class InternalException extends RuntimeException {

    private final String failMessage;

    public InternalException(String failMessage) {
        super(failMessage);
        this.failMessage = failMessage;
    }

    public InternalException(Throwable cause) {
        super(cause);
        this.failMessage = cause.getMessage();
    }

    public InternalException(String failMessage, Throwable cause) {
        super(cause);
        this.failMessage = failMessage;
    }
}
