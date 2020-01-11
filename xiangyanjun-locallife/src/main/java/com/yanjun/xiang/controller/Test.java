package com.yanjun.xiang.controller;

import java.lang.reflect.Proxy;

/**
 * @author XiangYanJun
 * @Date 2019/8/1 0001.
 */
public class Test {
    public static void main(String[] args) {
//        ByCarImpl byCar = new ByCarImpl();
//        byCar.byCar();
//        ByCarProxy byCarProxy = new ByCarProxy(byCar);
//        byCarProxy.byCar();

        ByCar byCar = new ByCarImpl();
        ByCar proxyBuyCar = (ByCar) Proxy.newProxyInstance(ByCar.class.getClassLoader(), new Class[]{ByCar.class}, new DynamicProxyHandler(byCar));
        proxyBuyCar.byCar();
    }

}
