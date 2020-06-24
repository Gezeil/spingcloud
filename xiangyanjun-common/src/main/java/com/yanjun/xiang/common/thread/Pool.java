package com.yanjun.xiang.common.thread;

public class Pool extends Thread {
    private int num = 0; // 出票数
    private int count = 10; // 剩余票数
    @Override
    public void run() {
        synchronized (this){
            while (true){
                if (count <= 0) {
                    break;
                }
                num++;
                count--;
                try {
                    Thread.sleep(500);// 模拟网络延时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("显示出票信息：" + Thread.currentThread().getName()
                        + "抢到第" + num + "张票，剩余" + count + "张票");
            }
        }
    }
}
