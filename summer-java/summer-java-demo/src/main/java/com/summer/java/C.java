package com.summer.java;

import java.util.ArrayList;
import java.util.List;

public class C {
    static class A {
    }

    static class B extends A {
    }

    static class D extends B {
    }

    public static void main(String[] args) {
        ArrayList<A> list = new ArrayList<A>();
        list.add(new B());
        method1(list);
    }

    private static void method1(List<? super A> list) {
        for (int i = 0; i < list.size(); i++) {
//            A a = list.get(0);
        }
    }
}