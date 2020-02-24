package com.summer.java.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
//        enhancer.setCallbackType(clazz);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        PerformanceMonior
        System.out.println("----------------begin-----"+method.getName() );
        set();
        Object result = methodProxy.invokeSuper(o, objects);
//        Object result = method.invoke(o, objects);
        System.out.println("----------------end-----"+method.getName() );

        return result;

    }

    @Override
    public void set() {
        System.out.println("----------------set------------------------" );

    }
}
