package com.yanjun.xiang.common.staticagent;

public class SimpleProxy implements Shape {
    private Shape shape;

    public SimpleProxy(Shape shape){
        this.shape = shape;
    }

    @Override
    public void draw() {
        System.out.println("create Rectangle");
        shape.draw();
    }

    @Override
    public void erase() {
        shape.erase();
        System.out.println("destroy Rectangle");
    }
}
