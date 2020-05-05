package com.yanjun.xiang.common.base;

import com.yanjun.xiang.common.annotation.Auth;
import com.yanjun.xiang.common.annotation.PostApi;
import com.yanjun.xiang.common.base.view.RestfulResponse;
import com.yanjun.xiang.common.base.vo.CurrentUser;
import com.yanjun.xiang.common.util.FormatUtil;
import com.yanjun.xiang.common.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spin.core.ErrorCode;
import org.spin.core.util.JsonUtils;
import org.spin.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Set;


/**
 * @author XiangYanJun
 * @Date 2019/7/29 0029.
 */
@Component
public class TestInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // 是否调用方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 认证信息判断
        Method method = ((HandlerMethod) handler).getMethod();

        Auth authAnno = AnnotatedElementUtils.getMergedAnnotation(method, Auth.class);
        if (null == authAnno) {
            responseWrite(response, ErrorCode.OTHER, "接口定义不正确");
            return false;
        }

        //boolean internal = internalRequest(request);
        //if (authAnno.scope() == ScopeType.INTERNAL && !internal) {
        //    responseWrite(response, ErrorCode.ACCESS_DENINED, "该接口仅允许内部调用: " + request.getRequestURI());
        //    return false;
        //}

//        if (authAnno.scope() == ScopeType.INTERNAL) {
//            if (!isInnerApi(request)) {
//                responseWrite(response, ErrorCode.ACCESS_DENINED, "请勿进行非法请求!" + request.getRemoteHost());
//                return false;
//            }
//            return true;
//        }

        // 用户信息
        Enumeration<String> enumeration = request.getHeaders(HttpHeaders.FROM);
        CurrentUser currentUser = null;
        if (enumeration.hasMoreElements()) {
            String user = enumeration.nextElement();
            if (StringUtils.isNotEmpty(user)) {
                currentUser = CurrentUser.setCurrent(StringUtils.urlDecode(user));
            }
        }

        boolean auth = authAnno.value();
        // if (auth && authAnno.scope() == ScopeType.OPEN_UNAUTH) {
        //     auth = !internal;
        // }

        ErrorCode errorCode = null;
        if (null == currentUser) {
            CurrentUser.clearCurrent();
            errorCode = ErrorCode.ACCESS_DENINED;
        } else if (ErrorCode.TOKEN_EXPIRED.getCode() == -currentUser.getId().intValue()) {
            CurrentUser.clearCurrent();
            errorCode = ErrorCode.TOKEN_EXPIRED;
        } else if (ErrorCode.TOKEN_INVALID.getCode() == -currentUser.getId().intValue()) {
            CurrentUser.clearCurrent();
            errorCode = ErrorCode.TOKEN_INVALID;
        }

        if (null != errorCode && errorCode != ErrorCode.ACCESS_DENINED) {
            logger.warn("非法的Token: {}", errorCode.toString());
        }

        if (!auth) {
            return true;
        } else if (null != errorCode) {
            responseWrite(response, errorCode);
            return false;
        }

        // 权限信息
        String[] permissions = authAnno.permissions();
        if (permissions.length == 0) {
            return true;
        }
//
//        Set<String> allPermissions = PermissionUtils.getUserPermissions(currentUser.getId());
//
//        if (allPermissions.containsAll(Arrays.asList(permissions))) {
//            return true;
//        }

        // 无效的授权
        responseWrite(response, ErrorCode.ACCESS_DENINED);

        return false;
    }

    private void responseWrite(HttpServletResponse response, ErrorCode errorCode, String... message) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setHeader("Encoded", "1");
            response.getWriter().write(JsonUtils.toJson(RestfulResponse
                    .error(errorCode, ((null == message || message.length == 0 || StringUtils.isEmpty(message[0])) ? errorCode.getDesc() : message[0]))));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
