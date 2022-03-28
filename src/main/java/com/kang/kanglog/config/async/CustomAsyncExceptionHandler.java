package com.kang.kanglog.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class CustomAsyncExceptionHandler
        implements AsyncUncaughtExceptionHandler {


    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Exception message - " + ex.getMessage());
        log.error("Method name - " + method.getName());
        for (Object param : params) {
            log.error("Parameter value - " + param);
        }

    }

}