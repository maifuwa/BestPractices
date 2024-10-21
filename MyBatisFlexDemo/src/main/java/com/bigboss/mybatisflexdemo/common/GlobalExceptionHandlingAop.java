package com.bigboss.mybatisflexdemo.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: maifuwa
 * @date: 2024/10/8 9:45
 * @description: 全局异常处理切面
 */
@Slf4j
@Aspect
@Component
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlingAop {

    private final Validator validator;

    @Pointcut("execution(* com.bigboss.mybatisflexdemo.controller.*.*(..))")
    public void allController() {
    }

    @Around("allController()")
    public CommonResult<?> around(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        CommonResult<?> result;

        try {
            validate(joinPoint.getArgs());
            result = (CommonResult<?>) joinPoint.proceed();
            log.info("service method {} executed successfully, cost time: {}ms", joinPoint.getSignature(), System.currentTimeMillis() - start);
        } catch (Throwable e) {
            result = dealWhitException(joinPoint, e);
        }

        return result;
    }

    private void validate(Object... objects) {
        for (Object object : objects) {
            Set<ConstraintViolation<Object>> violations = validator.validate(object);
            if (!violations.isEmpty()) {
                String errorMessage = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                throw new ConstraintViolationException(errorMessage, violations);
            }
        }
    }

    private CommonResult<?> dealWhitException(ProceedingJoinPoint joinPoint, Throwable e) {
        CommonResult<?> result;

        if (e instanceof ConstraintViolationException exception) {
            return dealWhitConstraintViolationException(exception);
        }

        if (e instanceof GlobalException exception) {
            result = CommonResult.failed(exception.getFailedCode(), e.getClass() + ": " + exception.getFailedMessage());
        } else {
            result = CommonResult.failed(HttpStatus.BAD_REQUEST.value(), e.getClass() + ": " + e.getMessage());
        }

        log.error("service method {} executed failed, exception: {}", joinPoint.getSignature(), e.getMessage(), e);
        return result;
    }

    private CommonResult<?> dealWhitConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> getArgName(constraintViolation.getPropertyPath().toString()) + constraintViolation.getMessage())
                .collect(Collectors.joining(", "));
        return CommonResult.failed(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    private String getArgName(String constraintViolationPath) {
        return constraintViolationPath.substring(constraintViolationPath.lastIndexOf('.') + 1);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public CommonResult<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return CommonResult.failed(HttpStatus.BAD_REQUEST.value(), "参数解析失败" + e.getMessage());
    }

}
