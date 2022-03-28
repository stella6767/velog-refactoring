package com.kang.kanglog.config.aop;


import lombok.extern.slf4j.Slf4j;
import net.lunalabs.mj.utills.common.CMResDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


@Slf4j
@Component
@Aspect
@Order(value = 2)
public class BindingAdvice {


    @Around("execution(* net.lunalabs.mj.web..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        String errorMsg = "";
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                log.info(bindingResult.toString());
                // 서비스 : 정상적인 화면 -> 사용자요청
                if (bindingResult.hasErrors()) {
                    //Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        //errorMap.put(error.getField(), error.getDefaultMessage());
                        errorMsg =  error.getField() + ": " + error.getDefaultMessage();
                    }

                    //throw new ValidateException(errorMsg);
                    //return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
                    return new CMResDto<>(HttpStatus.BAD_REQUEST, errorMsg, null);
                }
            }
        }
        return proceedingJoinPoint.proceed(); // 정상적이면 함수의 스택을 실행해라
    }



}
