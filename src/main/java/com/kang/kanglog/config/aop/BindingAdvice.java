package com.kang.kanglog.config.aop;


import com.kang.kanglog.utils.common.CMResDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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



    @Before("@annotation(com.kang.kanglog.config.anno.LoginCheck)")
    public void loginCheck(JoinPoint joinPoint) {
        //interceptor를 이용할까 하다가, 그냥 pontcut 적용

        Object[] args = joinPoint.getArgs();
        log.info("로그인 체크 메서드");
    }


    @Around("execution(* com.kang.kanglog.web..*Controller.*(..))")
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
