package com.yanjun.xiang.controller;

/**
 * @author XiangYanJun
 * @Date 2019/8/1 0001.
 */
public class ByCarProxy implements ByCar {

    private ByCar byCar;

    public ByCarProxy(ByCar byCar) {
        this.byCar = byCar;
    }

    @Override
    public void byCar() {
        System.out.println("我有钱");
        byCar.byCar();
        System.out.println("买完就没钱了");
    }
}
