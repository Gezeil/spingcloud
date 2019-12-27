package com.yanjun.xiang.common.agent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserManagerImplProxy implements InvocationHandler {

    private Object targetObject;

    public Object createObjectProxy(Object targetObject){
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object rect = null;
        //根据规则,只对add开始的方法代理,类型与Spring中代理实现(Pointcut,Advice)
        if(method.getName().startsWith("addUser")){
            checkScurity();
            //method.invoke()取得对象执行的某个方法并执行该方法
            rect = method.invoke(targetObject, args);
        }
        return rect;
    }
    //在代理类中根据需求需要添加的功能
    private void checkScurity(){
        System.out.println("-----------checkScurity()--------------");
    }
}
