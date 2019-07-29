package com.yanjun.xiang.common.base;

import java.lang.annotation.*;

/**
 * @author XiangYanJun
 * @Date 2019/7/27 0027.
 */
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CherryAnnotation {
    public boolean auth() default true;
}
