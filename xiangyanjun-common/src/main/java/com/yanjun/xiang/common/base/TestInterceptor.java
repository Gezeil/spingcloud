package com.yanjun.xiang.common.base;

import com.yanjun.xiang.common.annotation.PostApi;
import com.yanjun.xiang.common.util.JwtUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;


/**
 * @author XiangYanJun
 * @Date 2019/7/29 0029.
 */
@Component
public class TestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Method method = ((HandlerMethod) handler).getMethod();
        PostApi mergedAnnotation = AnnotatedElementUtils.getMergedAnnotation(method, PostApi.class);
        if (mergedAnnotation.auth()){

            String token = request.getHeader("token");
            if (token!=null){
                boolean result = JwtUtils.verify(token);
                if (result){
                    return true;
                }else {
                    response.getWriter().print("1");
                    return false;
                }
            }else {
                response.getWriter().print("1");
                return false;
            }
        }else {
            return true;
        }
    }

}
