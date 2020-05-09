package com.yanjun.xiang.common.base;

import com.alibaba.fastjson.JSON;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.yanjun.xiang.common.annotation.Auth;
import com.yanjun.xiang.common.base.view.RestfulResponse;
import com.yanjun.xiang.common.base.vo.CurrentUser;
import com.yanjun.xiang.common.entity.User;
import com.yanjun.xiang.common.throwable.AuthException;
import com.yanjun.xiang.common.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spin.core.ErrorCode;
import org.spin.core.util.JsonUtils;
import org.spin.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author XiangYanJun
 * @Date 2019/7/29 0029.
 */
@Component
public class TestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TestInterceptor.class);

    private DiscoveryClient discoveryClient;

    private EurekaClient eurekaClient;

    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 用户权限-用户角色集合（1,2,3）
     */
    public static final String USER_PERMISSION_ROLES = "user:permission:roles:";
    /**
     * 用户权限-角色对应菜单url集合(1:{"http://111","http://222"})
     */
    public static final String USER_PERMISSION_ROLE_MENUS = "user:permission:role:menus:";
    /**
     * 用户权限-所有菜单urls
     */
    public static final String USER_PERMISSION_MENUS = "user:permission:menus:";
    /**
     * token对应的authGroup
     */
    public static final String USER_TOKEN_AUTH_GROUP = "user:permission:token:authGroup:";

    /**
     * 判断请求是否来自feign
     */
    public static final String FROM_FEIGN = "fromFeign";

    /**
     * 是否权限校验标识
     */
    public static final String NEED_AUTH = "need.auth";


    public TestInterceptor() {
    }

    public TestInterceptor(DiscoveryClient discoveryClient, EurekaClient eurekaClient, RedisUtil redisUtil) {
        this.discoveryClient = discoveryClient;
        this.eurekaClient = eurekaClient;
        this.redisUtil = redisUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String host = request.getRemoteHost();

        // 是否调用方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 认证信息判断
        Method method = ((HandlerMethod) handler).getMethod();
        Auth authAnno = AnnotatedElementUtils.getMergedAnnotation(method, Auth.class);
        if (null == authAnno) {
            return this.handlerError(response, ErrorCode.OTHER, "接口定义不正确");
        }

        //------------------------------重构开始------------------------------------------------

        logger.info("authAnno:{}", authAnno);

//        获取网关携带过来的用户信息
        CurrentUser currentUser = getCurrentUser(request);
//        try {
//            //优先判断是否内部调用（无需token）
//            boolean isInternal = this.validateInternal(host, authAnno);
//            if(isInternal){
//                return true;
//            }
//
            boolean authValue = authAnno.value();
            if (!authValue) {
                return true;
            }

            //auth=true 再校验
            this.checkUser(currentUser);

            //解析token
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.isEmpty(header)) {
                throw new AuthException(ErrorCode.ACCESS_DENINED, "未授权的访问!" + host);
            }

            //解析token 根据token查询redis
            User authVo = this.getAuth(header);
//            AuthVo authVo = this.getAuth(header);
            logger.info("authVo:{}", JSON.toJSONString(authVo));
//            //处理兼容旧token
//            if (authVo == null || authVo.isEmpty()) {
//                return true;
//            }
//            //check 环境是否一致
//            if ("true".equals(ApolloKeyUtil.needAuthEnvApollo)) {
//                this.validateEnv(authVo);
//            }
//
//            //请求是否来自Feign
//            String fromFeign = request.getHeader(FROM_FEIGN);
//            if ("true".equals(ApolloKeyUtil.needAuthFeignApollo)) {
//                this.validateFeign(fromFeign, host);
//                if (isFeign(fromFeign)) {
//                    return true;
//                }
//            }
//
//            //判断是否来自第三方
//            boolean isThird = this.validateThird(host,authVo, authAnno);
//            if(isThird){
//                return true;
//            }
//
//            //校验来源
//            if ("true".equals(ApolloKeyUtil.needAuthSourceApollo)) {
//                this.validateSource(host, authVo, authAnno);
//            }
//
//            //校验权限组
//            if ("true".equals(ApolloKeyUtil.needAuthGroupApollo)) {
//                this.validateAuthGroup(authVo, authAnno, host);
//            }
//
//            //校验菜单
//            //开启url开关
//            if ("true".equals(ApolloKeyUtil.needAuthApollo)) {
//                this.validateAuthUrl(authVo, fromFeign, request, currentUser);
//            }
//
//        } catch (AuthException e) {
//            logger.error("auth  AuthException", e);
//            return this.handlerError(response, e.getExceptionType(), e.getSimpleMessage());
//
//        } catch (Exception e1) {
//            logger.error("auth  Exception", e1);
//            return this.handlerError(response, ErrorCode.OTHER, "未知异常");
//        }

        return true;
    }

//    /**
//     * 判断服务之间的环境是否一致，以登录uaac的环境为主
//     *
//     * @param authVo
//     * @throws AuthException
//     */
//    private void validateEnv(AuthVo authVo) throws AuthException {
//        String environment = authVo.getEnvironment();
//        String environmentApollo = SpringContextUtils.getActiveProfile();
//        if (StringUtils.isNotEmpty(environment) && StringUtils.isNotEmpty(environmentApollo) && !environment.equals(environmentApollo)) {
//            //token环境不匹配
//            throw new AuthException(ErrorCode.TOKEN_INVALID, "token环境不匹配");
//        }
//    }

//    /**
//     * 校验来源与接口的注解是否匹配
//     *
//     * @param host
//     * @param authVo
//     * @param authAnno
//     * @throws AuthException
//     */
//    private void validateSource(String host, AuthVo authVo, Auth authAnno) throws AuthException {
//        SourceType fromSource = authVo.getSource();
//        if (fromSource == null) {
//            throw new AuthException(ErrorCode.ACCESS_DENINED, String.format("token来源为空! %s", host));
//        }
//
//        //老项目scope注解
//        ScopeType scope = authAnno.scope();
//        //新项目source注解
//        SourceType[] sources = authAnno.sources();
//
//        boolean isPass = false;
//        //优先判断是否内部调用
//        if (scope == ScopeType.INTERNAL || scope == ScopeType.OPEN_UNAUTH || containsSourceType(sources, SourceType.INTERNAL)) {
//            //兼容老项目scope
//            if (isInnerApi(host)) {
//                isPass = true;
//            }
//        } else {
//            //若未配置source限制，通过
//            if (this.containsSourceType(sources, SourceType.DEFAULT)) {
//                return;
//            }
//            //非内部调用就判断source来源（运营工作台 or APP）
//            isPass = this.containsSourceType(sources, fromSource);
//        }
//
//        if (!isPass) {
//            throw new AuthException(ErrorCode.ACCESS_DENINED, String.format("请勿进行非法请求-请求来源! %s", host));
//        }
//    }

//    /**
//     * 校验是否内部调用
//     * @param host
//     * @param authAnno
//     * @throws AuthException
//     */
//    private boolean validateInternal(String host, Auth authAnno) throws AuthException {
//        //老项目scope注解
//        ScopeType scope = authAnno.scope();
//        //新项目source注解
//        SourceType[] sources = authAnno.sources();
//
//        boolean isPass = false;
//        //优先判断是否内部调用
//        if (scope == ScopeType.INTERNAL || scope == ScopeType.OPEN_UNAUTH || containsSourceType(sources, SourceType.INTERNAL)) {
//            //兼容老项目scope
//            if (isInnerApi(host)) {
//                isPass = true;
//            }
//            if (!isPass) {
//                throw new AuthException(ErrorCode.ACCESS_DENINED, String.format("请勿进行非法请求-仅内部调用! %s", host));
//            }
//        }
//        return isPass;
//    }

//    /**
//     * 校验是否第三方
//     * @param host
//     * @param authAnno
//     * @throws AuthException
//     */
//    private boolean validateThird(String host, AuthVo authVo,Auth authAnno) throws AuthException {
//        boolean isPass = false;
//        if (authAnno.thirdParty()==true || containsSourceType(authAnno.sources(),SourceType.THIRD)) {
//            if(authVo.isThird()){
//                isPass = true;
//            }
//            if (!isPass) {
//                throw new AuthException(ErrorCode.ACCESS_DENINED, String.format("请勿进行非法请求-第三方! %s", host));
//            }
//        }
//        return isPass;
//    }

    public boolean isFeign(String fromFeign) {
        return StringUtils.isNotEmpty(fromFeign);
    }

//    private void validateFeign(String fromFeign, String host) {
//        if (StringUtils.isNotEmpty(fromFeign) && !fromFeign.equals(ApolloKeyUtil.fromFeignApollo)) {
//            throw new AuthException(ErrorCode.ACCESS_DENINED, "非法Feign请求!" + host);
//        }
//    }

//    /**
//     * 校验权限组
//     *
//     * @param authVo
//     * @param authAnno
//     * @param host
//     */
//    private void validateAuthGroup(AuthVo authVo, Auth authAnno, String host) {
//        //目前只有运营端有authGroup，才需要校验
//        if (!authVo.isOperation()) {
//            return;
//        }
//        int authGroup = -1;
//        if (authVo.getAuthGroup() != null) {
//            authGroup = authVo.getAuthGroup();
//        }
//
//        int[] groups = authAnno.privilegeGroups();
//        if (groups[0] != -1 && !containsAuthGroup(groups, authGroup)) {
//            throw new AuthException(ErrorCode.ACCESS_DENINED, String.format("非法跨权限组请求! %s", host));
//        }
//    }

//    /**
//     * 校验url
//     *
//     * @param fromFeign
//     * @param request
//     * @param currentUser
//     */
//    private void validateAuthUrl(AuthVo authVo, String fromFeign, HttpServletRequest request, CurrentUser currentUser) {
//        if (this.needAuthUrl(authVo, fromFeign)) {
//            if (!this.permissionCheck(request, currentUser)) {
//                throw new AuthException(ErrorCode.USER_OPERATE_NO_PERMISSION);
//            }
//        }
//    }

//    /**
//     * feign、 third、 inner不校验url
//     *
//     * @param authVo
//     * @param fromFeign
//     * @return
//     */
//    public boolean needAuthUrl(AuthVo authVo, String fromFeign) {
//        return !authVo.isThird() && !authVo.isInner() && !this.isFeign(fromFeign);
//    }

    private User getAuth(String header) {
        String value = redisTemplate.opsForValue().get(header);
//        String value = redisUtil.getMemberValue(header);
        if (StringUtils.isEmpty(value)) {
            throw new AuthException(ErrorCode.TOKEN_EXPIRED);
        }
        return JSON.parseObject(value, User.class);
    }

    private void checkUser(CurrentUser currentUser) {
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
        //打印token错误码
        if (null != errorCode && errorCode != ErrorCode.ACCESS_DENINED) {
            logger.warn("非法的Token: {}", errorCode.toString());
        }
        if (errorCode != null) {
            throw new AuthException(errorCode);
        }
    }

    private CurrentUser getCurrentUser(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getHeaders(HttpHeaders.FROM);
        CurrentUser currentUser = null;
        if (enumeration.hasMoreElements()) {
            String user = enumeration.nextElement();
            if (StringUtils.isNotEmpty(user)) {
                currentUser = CurrentUser.setCurrent(StringUtils.urlDecode(user));
            }
        }
        return currentUser;
    }
    //------------------------------重构结束------------------------------------------------

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // do nothing
        try {
            CurrentUser.clearCurrent();
        } catch (Exception e) {
            logger.error("CurrentUser remove failed e", e.getMessage());
        }
    }

//    /**
//     * 判断请求是否来自内部
//     * <pre>
//     *     内部的定义:
//     *     1. 不允许来源于网关
//     *     2. 在白名单中，或者属于同一子网(不允许跨VLAN)，或者来源与注册中心中的其他服务
//     * </pre>
//     *
//     * @param request 请求
//     * @return 是否来自内部
//     */
//    private boolean internalRequest(HttpServletRequest request) {
//        return !StringUtils.toStringEmpty(request.getHeader(HttpHeaders.REFERER)).endsWith("GATEWAY")
//                && (whiteList.containsOne(request.getRemoteAddr(), request.getRemoteHost())
//                || NetworkUtils.inSameVlan(request.getRemoteHost()) || NetworkUtils.inSameVlan(request.getRemoteAddr()));
//    }

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

    /**
     * 是否为内部接口
     *
     * @param host 请求体
     * @return true or false
     */
    private boolean isInnerApi(String host) {
        List<String> servicesList = discoveryClient.getServices();
        // 从eureka客户端中拿到真实ip地址集合，然后转换set去重
        Set<String> collect = servicesList.stream().filter(service -> !"bnd-gateway".equalsIgnoreCase(service))
                .flatMap(serviceName -> eurekaClient.getInstancesByVipAddress(serviceName, false)
                        .stream()).map(InstanceInfo::getIPAddr).collect(Collectors.toSet());

        return collect.contains(host);
    }

    /**
     * 进行url过滤
     *
     * @param request
     * @param currentUser
     * @return
     */
//    private boolean permissionCheck(HttpServletRequest request, CurrentUser currentUser) {
//        try {
//            String contextPath = request.getServletPath();
//            if (StringUtils.isEmpty(contextPath)) {
//                return true;
//            }
//            contextPath = request.getMethod() + ":" + contextPath;
//            //获取权限组
//            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//            if (StringUtils.isEmpty(header)) {
//                // 无效的授权
//                throw new BizException(ErrorCode.INTERNAL_ERROR, "permissionCheck Authorization为空");
//            }
//            //兼容前端及第三方接口（判断不鉴权）
//            String authGroupAndBusinessId = redisUtil.getMemberValue(USER_TOKEN_AUTH_GROUP + header);
//            if (StringUtils.isEmpty(header) || StringUtils.isEmpty(authGroupAndBusinessId)) {
//                return true;
//            }
//            //去除多余引号
//            authGroupAndBusinessId = authGroupAndBusinessId.replaceAll("\\\"", "");
//            String[] authGroupArr = authGroupAndBusinessId.split(",");
//            if (authGroupArr != null && authGroupArr.length >= 2) {
//                List<String> urls = new ArrayList<>();
//                //获取用户所有角色
//                String roleIds = redisUtil.getMemberValue(USER_PERMISSION_ROLES + currentUser.getId() + ":" + authGroupArr[0] + ":" + authGroupArr[1]);
//                if (StringUtils.isNotEmpty(roleIds)) {
//                    //去除多余引号
//                    roleIds = roleIds.replaceAll("\\\"", "");
//                    String[] roleIdArray = roleIds.split(",");
//                    if (roleIdArray != null && roleIdArray.length > 0) {
//                        for (String roleId : roleIdArray) {
//                            //根据roleId获取菜单urls
//                            String roleUrls = redisUtil.getMemberValue(USER_PERMISSION_ROLE_MENUS + Integer.parseInt(roleId));
//                            if (StringUtils.isNotEmpty(roleUrls)) {
//                                List<String> urlList = JSON.parseArray(roleUrls, String.class);
//                                if (!CollectionUtils.isEmpty(urlList)) {
//                                    urls.addAll(urlList);
//                                }
//                            }
//                        }
//                    }
//                }
//                //获取权限组下所有菜单
//                String menus = redisUtil.getMemberValue(USER_PERMISSION_MENUS + authGroupArr[1]);
//                List<String> allAuthUrls = null;
//                if (StringUtils.isNotEmpty(menus)) {
//                    allAuthUrls = JSON.parseArray(menus, String.class);
//                }
//                //优先判断小的
//                //小的没有 大的有
//                if ((CollectionUtils.isEmpty(urls) || !isMatched(contextPath, urls))
//                        && !CollectionUtils.isEmpty(allAuthUrls) && isMatched(contextPath, allAuthUrls)) {
//                    logger.error("no auth userId : {} , path : {}", currentUser.getId(), contextPath);
//                    return false;
//                }
//            }
//        } catch (Exception e) {
//            logger.error("permissionCheck error - {}", e.getMessage());
//            return false;
//        }
//        return true;
//    }

    /**
     * 匹配请求url
     *
     * @param path
     * @param urls
     * @return
     */
    private boolean isMatched(String path, List<String> urls) {
        boolean matched = false;
        if (StringUtils.isNotEmpty(path) && !CollectionUtils.isEmpty(urls)) {
            //匹配url
            AntPathMatcher matcher = new AntPathMatcher();
            for (String url : urls) {
                if (StringUtils.isNotEmpty(url) && matcher.match(url, path)) {
                    matched = true;
                    break;
                }
            }
        }
        return matched;
    }

    /**
     * 判断用户来源是否匹配
     *
     * @param sourceTypes
     * @param sourceType
     * @return
     */
//    private boolean containsSourceType(SourceType[] sourceTypes, SourceType sourceType) {
//        boolean contains = false;
//        if (sourceTypes != null && sourceTypes.length > 0) {
//            for (SourceType type : sourceTypes) {
//                if (type == sourceType) {
//                    contains = true;
//                    break;
//                }
//            }
//        }
//        return contains;
//    }

    /**
     * 判断权限组是否匹配
     *
     * @param groups
     * @param authGroup
     * @return
     */
    private boolean containsAuthGroup(int[] groups, int authGroup) {
        boolean contains = false;
        if (groups != null && groups.length > 0) {
            for (int group : groups) {
                if (group == authGroup) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    public boolean handlerError(HttpServletResponse response, ErrorCode errorCode, String... message) {
        this.responseWrite(response, errorCode, message);
        return false;
    }
}
