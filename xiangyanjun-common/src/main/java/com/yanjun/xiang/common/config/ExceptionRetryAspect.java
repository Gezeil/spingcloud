package com.yanjun.xiang.common.config;

import com.yanjun.xiang.common.annotation.ExceptionRetry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ExceptionRetryAspect {


    @Pointcut("@annotation(com.yanjun.xiang.common.annotation.ExceptionRetry)")
    public void retryPointCut(){

    }

    @Around("retryPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        ExceptionRetry exceptionRetry = method.getAnnotation(ExceptionRetry.class);
        String name = method.getName();
        return null;
    }

}
