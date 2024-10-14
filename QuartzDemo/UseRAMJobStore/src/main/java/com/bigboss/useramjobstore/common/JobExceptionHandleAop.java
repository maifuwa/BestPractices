package com.bigboss.useramjobstore.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: maifuwa
 * @date: 2024/10/14 21:22
 * @description:
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class JobExceptionHandleAop {

    @Pointcut("execution(public void com.bigboss.useramjobstore.job.*.execute(..))")
    public void allRealJob() {
    }

    @Around("allRealJob()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            dealWith(proceedingJoinPoint, e);
            throw new InternalException(e);
        }
    }

    private void dealWith(ProceedingJoinPoint proceedingJoinPoint, Throwable cause) {
        if (cause instanceof InternalException e) {
            log.error("job: {}, execute fail for {}", proceedingJoinPoint.getSignature(), e.getFailMessage(), e);
        } else {
            log.error("job: {}, execute fail for {}", proceedingJoinPoint.getSignature(), cause.getMessage(), cause);
        }
    }

}
