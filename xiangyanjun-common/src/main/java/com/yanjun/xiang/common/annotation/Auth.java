package com.yanjun.xiang.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description 授权
 *
 * @author wangy QQ 837195190
 * <p>Created by wangy on 2019/3/14.</p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 是否需要认证，默认true
     *
     * @return 是否需要认证
     */
    boolean value() default true;

    /**
     * 授权资源名称
     *
     * @return 授权资源名称字符串
     */
    String name() default "";

    /**
     * 授权信息
     *
     * @return 所需权限列表
     */
    String[] permissions() default {};

    /**
     * 是否仅内部调用
     * <pre>
     *     内部调用，意味着该接口仅能由服务间相互调用，不允许由网关分发。
     * </pre>
     *
     * @return 是否仅内部调用
     */
    boolean internal() default false;
}
