package com.yanjun.xiang.common.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @author XiangYanJun
 * @Date 2019/7/29 0029.
 */
@Component
public class TestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ((HandlerMethod) handler).getMethod();
        CherryAnnotation mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(method, CherryAnnotation.class);
        if (mergedAnnotation.auth()){
            System.out.println(""+mergedAnnotation.auth());
        }else {
            System.out.println(""+mergedAnnotation.auth());
        }
        System.out.println("===================自定义拦截器=================");
        if (handler instanceof HandlerMethod) {
            String requestURI = request.getRequestURI();
            System.out.println("当前请求路径是:{}" + requestURI);
        }
        return true;
    }

}
