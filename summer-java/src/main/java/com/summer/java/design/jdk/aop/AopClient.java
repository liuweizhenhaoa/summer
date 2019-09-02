package com.summer.java.design.jdk.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class AopClient {


    public static void main(String[] args) {

//        System.getProperties().put("sun.misc.ProxyGenerator.saveGenerateFiles", "true");
        UserMgr mgr = new UserMgrImpl();

        //为用户管理添加事务处理
        InvocationHandler h = new MyInvocationHandler(mgr);
        UserMgr u = (UserMgr) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),mgr.getClass().getInterfaces(),h);

//        //为用户管理添加显示方法执行时间的功能
//        TimeHandler h2 = new TimeHandler(u);
//        u = (UserMgr)Proxy.newProxyInstance(UserMgr.class,h2);

        u.addUser();
        System.out.println("\r\n==========华丽的分割线==========\r\n");
        u.delUser();

    }
}
