package com.summer.java.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        System.out.println("----------------begin-----"+method.getName() );
        Object result = method.invoke(object, objects);
        System.out.println("----------------end"+method.getName());

        return result;
    }
}
