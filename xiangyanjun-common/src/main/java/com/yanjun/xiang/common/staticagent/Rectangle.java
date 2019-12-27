package com.yanjun.xiang.common.staticagent;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("draw Rectangle");
    }

    @Override
    public void erase() {
        System.out.println("erase Rectangle");
    }
}
