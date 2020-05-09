package com.yanjun.xiang.common.base;

import com.alibaba.fastjson.JSON;
import com.yanjun.xiang.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spin.core.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserEnterpriseIntercepter implements HandlerInterceptor {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserEnterpriseIntercepter.class);

    private final RedisUtil redisUtil;

    public static final String REDIS_TOKEN = "user:access:token:";

    public UserEnterpriseIntercepter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 是否调用方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

//        if (!StringUtils.isEmpty(header)) {
//            String substring = header.substring(header.lastIndexOf(".") + 1);
//            String value = redisUtil.getMemberValue(REDIS_TOKEN.concat(substring));
//            EnterpriseVo enterpriseVo = JSON.parseObject(value, EnterpriseVo.class);
//            if (enterpriseVo != null && enterpriseVo.getOrganId() != null && enterpriseVo.getOrganId() > 0) {
//                CurrentEnterprise.setValue(enterpriseVo.getOrganId());
//            } else {
//                CurrentEnterprise.clearCurrent();
//                CurrentEnterprise.setValue(0L);
//            }
//        }else {
//            LOGGER.info("UserEnterpriseIntercepter header >>> 为空");
//            CurrentEnterprise.clearCurrent();
//            CurrentEnterprise.setValue(0L);
//        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //CurrentEnterprise.setValue(0L);
//        try {
//            CurrentEnterprise.clearCurrent();
//        } catch (Exception e) {
//            LOGGER.error("CurrentEnterprise remove failed e", e.getMessage());
//        }
    }

}
