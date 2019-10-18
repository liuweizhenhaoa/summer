package com.summer.java.aop.cglib;

import com.summer.java.aop.jdk.UserMgr;
import com.summer.java.aop.jdk.UserMgrImpl;


/**
 * 基于子类化的CGLIB代理
 */
public class CglibAop {


    public static void main(String[] args) {
        //生成代理类到本地
//        System.setProperty("", "");
        UserMgrImpl userMgr = new UserMgrImpl();
        CglibProxy cp = new CglibProxy();
        UserMgr proxy = (UserMgr) cp.getProxy(userMgr.getClass());

        proxy.addUser();
        proxy.delUser();

        System.out.println(UserMgrImpl.class.isInterface());


    }
}
