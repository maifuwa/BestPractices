package com.bigboss.usedjdbcjobstore.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: maifuwa
 * @date: 2024/10/8 9:45
 */
@Slf4j
@Aspect
@Component
public class GlobalExceptionHandlingAop {

    @Pointcut("execution(com.bigboss.usedjdbcjobstore.common.CommonResult com.bigboss.usedjdbcjobstore.controller.*.*(..))")
    public void allController() {
    }

    @Around("allController()")
    public CommonResult<?> around(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        CommonResult<?> result;

        try {
            result = (CommonResult<?>) joinPoint.proceed();
            log.info("service method {} executed successfully, cost time: {}ms", joinPoint.getSignature(), System.currentTimeMillis() - start);
        } catch (Throwable e) {
            result = dealWhitException(joinPoint, e);
        }

        return result;
    }

    private CommonResult<?> dealWhitException(ProceedingJoinPoint joinPoint, Throwable e) {
        CommonResult<?> result;
        if (e instanceof GlobalException exception) {
            result = CommonResult.failed(exception.getFailedCode(), exception.getMessage());
        } else {
            result = CommonResult.failed(e.getMessage());
        }
        log.error("service method {} executed failed, exception: {}", joinPoint.getSignature(), e.getMessage(), e);
        return result;
    }

}
