package com.kang.kanglog.config.aop;


import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.handler.custom_exception.NoLoginException;
import com.kang.kanglog.utils.common.CMResDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
@Aspect
@Order(value = 1)
public class BindingAdvice {


    @Before("@annotation(com.kang.kanglog.config.anno.LoginCheck)")
    public void loginCheck(JoinPoint joinPoint) {
        //사실 시큐리티에서 다 처리 로직을 짯긴 한데.. 컨트롤러 메서드 단위 별로 또 걸고 싶기도 해서.

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.info("Login Check Method :: " + methodSignature.toString());

        PrincipalDetails details = null;
        HttpServletResponse response = null;


        for (Object arg: joinPoint.getArgs()) {
            if (arg instanceof PrincipalDetails) {
                details = (PrincipalDetails) arg;
                log.info("details :: " +  details);

            }else if (arg instanceof HttpServletResponse){
                response = (HttpServletResponse) arg;
            }
        }

        /**
         * 바로 response.sendRedirect 는 안 되나보다. 프론트에서 로그아웃처리..
         */


        if (details == null) {
            log.info("???????????????");
            throw new NoLoginException();
        }

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
