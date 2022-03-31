package com.kang.kanglog.config.aop;


import com.kang.kanglog.config.security.PrincipalDetails;
import com.kang.kanglog.web.dto.post.PostReqDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect ///AOP 설정 클래스
@Order(value = 2) //AOP 순서
public class LoggerAspect {

    //참고: https://makeinjava.com/logging-aspect-restful-web-service-spring-aop-request-response/
    //HTTP Response 까지 다 보고 싶다면,요걸 참고하면 될듯, https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl
    //https://hyunsoori.tistory.com/12
    //https://velog.io/@sixhustle/log  https://prohannah.tistory.com/182

    @Pointcut("execution(* com.kang.kanglog.web..*Controller.*(..))")
    private void controllerCut() {
    }

    @Pointcut("within(com.kang.kanglog.service..*)")
    private void serviceCut() {
    }


//    @Pointcut("execution(* *.*(..))")
//    protected void allMethodCut() {
//    }

    @Before("serviceCut()")
    public void serviceLoggerAdvice(JoinPoint joinPoint) {
        String type = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().getName();
        log.info("Service type : " + type + " ,  method : " + method);
    }


    @Before("controllerCut()")
    public void requestLoggerAdvice(JoinPoint joinPoint) {

        String type = joinPoint.getSignature().getDeclaringTypeName();
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("requset url : " + request.getServletPath());
        log.info("type : " + type);
        log.info("method : " + method);

        for (Object arg : args) {
            if (arg instanceof PostReqDto.PostSaveReqDto) {
                //base64를 제외하고 표시할려고 하다가 그냥 생략하자..
            }else{
                log.info("agrs=>{}",args);
            }
        }
    }


    //@Around("execution(* com.kang.kanglog.web..*Controller.*(..)) || within(com.kang.kanglog.service..*)")
    public Object loggerAdviceServiceAndController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if (type.indexOf("Service") > -1) {
            log.info("Service type : " + type + " ,  method : " + method);
        }

        if (type.indexOf("Controller") > -1) {
            log.info("requset url : " + request.getServletPath());
            log.info("type : " + type);
            log.info("method : " + method);
            //log.info("agrs=>{}",args);
        }


        return proceedingJoinPoint.proceed();
    }


    @AfterReturning(pointcut = "controllerCut()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        //interceptor를 이용할까 하다가, 그냥 pontcut 적용
        log.info(joinPoint.getSignature().getName() + ",   Method Return value : " + result);
    }

    @AfterThrowing(pointcut = "controllerCut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        log.error("Cause : " + exception.getCause());
    }


    @Around("@annotation(com.kang.kanglog.config.anno.StopWatch)")
    public Object stopWatch(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        Object proceed = null;
        String methodName = joinPoint.getSignature().getName();
        try {
            stopWatch.start();
            proceed = joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("{} elapsed time :: {}", methodName, stopWatch.getTotalTimeMillis());
        }

        return proceed;
    }


}