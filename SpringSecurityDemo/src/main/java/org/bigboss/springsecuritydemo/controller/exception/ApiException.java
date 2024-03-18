package org.bigboss.springsecuritydemo.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: maifuwa
 * @date: 2024/3/18 下午8:12
 * @description:
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiException extends RuntimeException {

        public ApiException(String message) {
            super(message);
        }
}
