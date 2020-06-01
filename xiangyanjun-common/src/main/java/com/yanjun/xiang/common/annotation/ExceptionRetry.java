package com.yanjun.xiang.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionRetry {
    /**
     * 设置失败之后重试次数，默认为1次。
     * 少于1次，则默认为1次
     * 推荐最好不要超过5次, 上限为10次
     * 当没有重试次数时, 会将异常重新抛出用来定位问题。
     *
     * @return
     */
    int times() default 1;

    /**
     * 重试等待时间，时间单位为毫秒。默认是 0.5 * 1000ms, 小于等于0则不生效
     * 推荐不要超过 3 * 1000ms
     * 上限为 10 * 1000ms
     *
     * @return
     */
    long waitTime() default 500;

    /**
     * 需要抛出的异常, 这些异常发生时, 将直接报错, 不再重试。
     * 传入一些异常的class对象
     * 如UserException.class
     * 当数组长度为0时, 那么都不会抛出, 会继续重试
     *
     * @return 异常数组
     */
    Class[] needThrowExceptions() default {};

    /**
     * 需要捕获的异常, 如果需要捕获则捕获重试。否则抛出异常
     * 执行顺序 needThrowExceptions --> catchExceptions 两者并不兼容
     * 当 needThrowExceptions 判断需要抛出异常时, 抛出异常, 否则进入此方法, 异常不在此数组内则抛出异常
     * 当数组长度为0时, 不会执行捕获异常的逻辑。
     *
     * @return 异常数组
     */
    Class[] catchExceptions() default {};
}
