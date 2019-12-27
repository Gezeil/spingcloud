package com.yanjun.xiang.common.staticagent;

import java.awt.*;
import java.awt.event.InputEvent;

public class SimpleProxyDemo {


    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(1826, 10);
        pressMouse(robot, InputEvent.BUTTON1_MASK, 500);
    }

    private static void pressMouse(Robot r,int m,int delay){
        r.mousePress(m);
        r.delay(10);
        r.mouseRelease(m);
        r.delay(delay);
    }
}
