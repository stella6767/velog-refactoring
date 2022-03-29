package com.kang.kanglog.handler.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;


public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {


    /**
     * AOP로 대체하겠다.. 이 오브젝트는 안 쓴다는 의미.
     * @param parameter
     * @return
     */


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        boolean isPrincipalAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
//        boolean isPrincipalClass = Principal.class.equals(parameter.getParameterType());
//
//        return isPrincipalAnnotation && isPrincipalClass;

        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //httpSession.getAttribute("principal")
        return null;
    }
}