package com.yanjun.xiang.common.agent;

public class Client {
    public static void main(String[] args) {
//        UserManager userManager = new UserManagerImpl();
        UserManagerImplProxy proxy = new UserManagerImplProxy();
        UserManager userManager = (UserManager) proxy.createObjectProxy(new UserManagerImpl());
        userManager.addUser("张三", "123456");
    }
}
