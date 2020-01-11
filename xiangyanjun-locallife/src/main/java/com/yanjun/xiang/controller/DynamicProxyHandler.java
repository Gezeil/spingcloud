package com.yanjun.xiang.controller;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author XiangYanJun
 * @Date 2019/8/1 0001.
 */
public class DynamicProxyHandler implements InvocationHandler {

    public Object object;

    public DynamicProxyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我有钱");
        Object result = method.invoke(object, args);
        System.out.println("买完就没钱了");
        return result;
    }
}
